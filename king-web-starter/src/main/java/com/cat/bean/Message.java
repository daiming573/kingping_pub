package com.cat.bean;


public enum Message {

    /** 登录错误码 */
    LOGIN_FAILURE {
        @Override
        public String toString() {
            return "nameOrpasswordWrong";
        }
    },
    LOGIN_RANDCODE {
        @Override
        public String toString() {
            return "codeWrong";
        }
    },
    User_status {
        @Override
        public String toString() {
            return "codeWrong";
        }
    },
    Update_Password {
        @Override
        public String toString() {
            return "mustUpdatePassword";
        }
    }

}
