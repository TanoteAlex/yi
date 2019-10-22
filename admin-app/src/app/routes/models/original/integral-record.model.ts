import {Member} from "./member.model";
import {IntegralTask} from "./integral-task.model";

export class IntegralRecord {

    /**
       * 积分记录ID
     */
    id: number;

    /**
       * GUID
     */
    guid: string;

    /**
       * 会员（member表ID）
     */
    member: Member;

    /**
       * 积分任务（integral_task表ID）
     */
    integralTask: IntegralTask;

    /**
     * 任务名称
     */
    taskName: string;

    /**
       * 操作类型（0增加1减少）
     */
    operateType: number;

    /**
       * 增/减积分数值
     */
    operateIntegral: number;

    /**
       * 当前积分
     */
    currentIntegral: number;

    /**
       * 创建时间
     */
    createTime: string;
}
