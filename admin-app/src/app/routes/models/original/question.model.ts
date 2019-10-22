import {QuestionType} from "./question-type.model";

export class Question {

    /**
       * 问题ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 问题类型(question_type表ID)
     */
    questionTypeId:number;

    questionType:QuestionType

    /**
       * 提出问题
     */
    askQuestion:string;

    /**
       * 回答问题
     */
    answerQuestion:string;

    /**
       * 排序
     */
    sort:number;

    /**
       * 显示（0显示1不显示）
     */
    state:number;

    /**
       * 创建时间
     */
    createTime:string;

    /**
       * 删除（0否1是）
     */
    deleted:number;

    /**
       * 删除时间
     */
    delTime:string;
}
