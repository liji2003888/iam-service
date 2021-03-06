package io.choerodon.iam.infra.mapper;

import io.choerodon.iam.api.query.ApplicationQuery;
import io.choerodon.iam.infra.dto.ApplicationDTO;
import io.choerodon.mybatis.common.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author superlee
 * @since 0.15.0
 */
public interface ApplicationMapper extends Mapper<ApplicationDTO> {
    /**
     * 模糊查询
     *
     * @param applicationSearchDTO
     * @return
     */
    List fuzzyQuery(@Param("applicationSearchDTO") ApplicationQuery applicationSearchDTO);

    /**
     * 传入application id集合，返回application 对象集合
     *
     * @param idSet
     * @return
     */
    List matchId(@Param("idSet") Set<Long> idSet);

    /**
     * 查应用附带项目信息
     *
     * @param organizationId
     * @return
     */
    List<ApplicationDTO> selectWithProject(@Param("organizationId") Long organizationId);
}
