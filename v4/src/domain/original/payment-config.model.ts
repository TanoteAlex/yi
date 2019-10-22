export class PaymentConfig {

    /**
     * 支付配置ID
     */
    id: number;

    /**
     * GUID
     */
    guid: string;

    /**
     * 支付类型（1支付宝2微信）
     */
    payType: boolean;

    /**
     * 支付宝分配给开发者的应用ID
     */
    appId: string;

    /**
     * 接口名称
     */
    method: string;

    /**
     * 请求使用的编码格式
     */
    charset: string;

    /**
     * 商户生成签名字符串所使用的签名算法类型
     */
    signType: string;

    /**
     * 商户请求参数的签名串
     */
    sign: string;

    /**
     * 调用的接口版本，固定为：1.0
     */
    version: string;

    /**
     * 支付宝服务器主动通知商户服务器里指定的页面http/https路径。建议商户使用https
     */
    notifyUrl: string;

    /**
     * 商户收款账号
     */
    mchId: string;

    /**
     * 交易过程生成签名的密钥
     */
    appKey: string;

    /**
     * APPID对应的接口密码
     */
    appSecret: string;

    /**
     * 创建时间
     */
    createTime: string;

    /**
     * 删除（0否1是）
     */
    deleted: boolean;

    /**
     * 删除时间
     */
    delTime: string;
}
