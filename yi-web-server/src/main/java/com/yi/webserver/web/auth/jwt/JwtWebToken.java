package com.yi.webserver.web.auth.jwt;

import static com.yi.base.ProjectConstants.USER_CODE;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Jwt token工具
 */
public class JwtWebToken {

	private final static Logger LOG = LoggerFactory.getLogger(JwtWebToken.class);

	public final static String TOKEN_HEADER = "web-token";

	private final static String SERCET_KEY = "web-token-y001";

	private final static byte[] API_KEY_SECRET_BYTES = DatatypeConverter.parseBase64Binary(SERCET_KEY);

	// 过期时间7天
	private final static long KEEP_TIME = 7 * 24 * 60 * 60 * 1000;

	/**
	 * 生成token
	 * 
	 * @param userId
	 * @param userCode
	 * @param issuer
	 * @param subject
	 * @return
	 */
	public static String generateToken(int userId, String userCode, String issuer, String subject) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		Key signingKey = new SecretKeySpec(API_KEY_SECRET_BYTES, signatureAlgorithm.getJcaName());

		// 签发时间
		Date iat = new Date();
		// 失效时间
		Date exp = new Date(iat.getTime() + KEEP_TIME);
		JwtBuilder builder = Jwts.builder().setId(String.valueOf(userId)).setIssuedAt(new Date());

		if (subject != null) {
			builder.setSubject(subject);
		}
		if (issuer != null) {
			builder.setIssuer(issuer);
		}
		if (userCode != null) {
			builder.claim(USER_CODE, userCode);
		}

		builder.signWith(signatureAlgorithm, signingKey);
		builder.setExpiration(exp);
		// if (keeptime >= 0) {
		// Date exp = new Date(System.currentTimeMillis() + keeptime);
		// builder.setExpiration(exp);
		// }
		return builder.compact();
	}

	public static String updateToken(String token) {
		try {
			Claims claims = getClaimsByToken(token);

			String id = claims.getId();
			String subject = claims.getSubject();
			String issuer = claims.getIssuer();
			String userCode = (String) claims.get(USER_CODE);
			return generateToken(Integer.parseInt(id), userCode, issuer, subject);
		} catch (Exception ex) {
			LOG.info("JWT令牌更新失败, token={}", token, ex);
		}
		return null;
	}

	public static String updateTokenBase64Code(String token) {
		Base64.Encoder base64Encoder = Base64.getEncoder();
		Base64.Decoder decoder = Base64.getDecoder();
		try {
			token = new String(decoder.decode(token), "utf-8");
			Claims claims = getClaimsByToken(token);

			String id = claims.getId();
			String subject = claims.getSubject();
			String issuer = claims.getIssuer();
			String userCode = (String) claims.get(USER_CODE);

			String newToken = generateToken(Integer.parseInt(id), userCode, issuer, subject);
			return base64Encoder.encodeToString(newToken.getBytes());

		} catch (Exception ex) {
			LOG.info("JWT令牌更新失败, token={}", token, ex);
		}
		return null;
	}

	/**
	 * 通过 token 获取 Claims
	 * 
	 * @param token
	 * @return
	 */
	public static Claims getClaimsByToken(String token) {
		return Jwts.parser().setSigningKey(API_KEY_SECRET_BYTES).parseClaimsJws(token).getBody();
	}

	/**
	 * 校验token
	 * 
	 * @param token
	 * @return true 验证通过 false 验证不通过
	 */
	public static boolean verifyToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(API_KEY_SECRET_BYTES).parseClaimsJws(token).getBody();
		if (claims.getExpiration().after(new Date())) {
			return true;
		}
		return false;
	}

	public static WebToken getToken(String token) {
		LOG.debug("解析JWT令牌 {}", token);
		try {
			Claims claims = getClaimsByToken(token);

			String id = claims.getId();
			String subject = claims.getSubject();
			String issuer = claims.getIssuer();
			String userCode = (String) claims.get(USER_CODE);
			WebToken tk = new WebToken(Integer.parseInt(id), userCode, issuer, subject);

			LOG.debug("JWT令牌解析成功 {}", tk);
			return tk;

		} catch (Exception ex) {
			LOG.info("JWT令牌解析失败, token={}", token, ex);
		}
		return null;
	}

	public static WebToken getSupplierToken(HttpServletRequest request) {
		String token = getTokenFromRequest(request);
		LOG.info("解析JWT令牌 {}", token);
		try {
			Claims claims = getClaimsByToken(token);
			String id = claims.getId();
			String subject = claims.getSubject();
			String issuer = claims.getIssuer();
			String userCode = (String) claims.get(USER_CODE);
			WebToken tk = new WebToken(Integer.parseInt(id), userCode, issuer, subject);
			LOG.info("JWT令牌解析成功 {}", tk);
			return tk;
		} catch (Exception ex) {
			LOG.error("JWT令牌解析失败, token={}", token, ex);
		}
		return null;
	}

	/**
	 * 从请求信息中获取token值
	 *
	 * @param request
	 *            request
	 * @return token值
	 */
	public static String getTokenFromRequest(HttpServletRequest request) {
		// 默认从header里获取token值
		String token = request.getHeader(JwtWebToken.TOKEN_HEADER);
		if (StringUtils.isBlank(token)) {
			// 从请求信息中获取token值
			token = request.getParameter(JwtWebToken.TOKEN_HEADER);
		}
		return token;
	}
}