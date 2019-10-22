import {User} from "./user.model";
import {Role} from "./role.model";

export class UserRole {

    /**
     * 用户（user表id）
     */
    userId: User;

    /**
     * 角色（role表id）
     */
    roleId: Role;
}
