package io.choerodon.iam.infra.exception;

import io.choerodon.core.exception.CommonException;

/**
 * 不合法参数异常
 *
 * @author superlee
 * @since 2019-07-11
 */
public class IllegalArgumentException extends CommonException {
    public IllegalArgumentException(String code, Object... parameters) {
        super(code, parameters);
    }

    public IllegalArgumentException(String code, Throwable cause, Object... parameters) {
        super(code, cause, parameters);
    }

    public IllegalArgumentException(String code, Throwable cause) {
        super(code, cause);
    }

    public IllegalArgumentException(Throwable cause, Object... parameters) {
        super(cause, parameters);
    }
}
