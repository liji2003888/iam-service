package io.choerodon.iam.api.controller.v1;

import java.util.List;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import io.choerodon.base.annotation.Permission;
import io.choerodon.base.domain.PageRequest;
import io.choerodon.base.domain.Sort;
import io.choerodon.base.enums.ResourceType;
import io.choerodon.core.base.BaseController;
import io.choerodon.iam.api.query.RoleQuery;
import io.choerodon.iam.app.service.PermissionService;
import io.choerodon.iam.app.service.RoleService;
import io.choerodon.iam.infra.common.utils.ParamUtils;
import io.choerodon.iam.infra.dto.PermissionDTO;
import io.choerodon.iam.infra.dto.RoleDTO;
import io.choerodon.mybatis.annotation.SortDefault;
import io.choerodon.swagger.annotation.CustomPageRequest;


/**
 * @author superlee
 * @author wuguokai
 */
@RestController
@RequestMapping(value = "/v1/roles")
public class RoleController extends BaseController {

    private RoleService roleService;
    private PermissionService permissionService;

    public RoleController(RoleService roleService, PermissionService permissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    /**
     * 分页查询角色
     *
     * @return 查询结果
     */
    @Permission(type = ResourceType.SITE)
    @ApiOperation(value = "分页查询角色")
    @PostMapping(value = "/search")
    @CustomPageRequest
    public ResponseEntity<PageInfo<RoleDTO>> pagedSearch(@ApiIgnore
                                                         @SortDefault(value = "id", direction = Sort.Direction.DESC) PageRequest pageRequest,
                                                         @RequestBody RoleQuery roleQuery) {
        return new ResponseEntity<>(roleService.pagingSearch(pageRequest, roleQuery), HttpStatus.OK);
    }

    @Permission(permissionWithin = true)
    @ApiOperation(value = "通过label查询关联角色列表")
    @GetMapping(value = "/id")
    public ResponseEntity<List<Long>> queryIdsByLabelNameAndLabelType(@RequestParam(value = "label_name") String labelName,
                                                                      @RequestParam(value = "label_type") String labelType) {
        return new ResponseEntity<>(roleService.queryIdsByLabelNameAndLabelType(labelName, labelType), HttpStatus.OK);
    }

    /**
     * 根据角色id查询角色
     *
     * @return 查询结果
     */
    @Permission(type = ResourceType.SITE)
    @ApiOperation(value = "通过id查询角色")
    @GetMapping(value = "/{id}")
    public ResponseEntity<RoleDTO> queryWithPermissionsAndLabels(@PathVariable Long id) {
        return new ResponseEntity<>(roleService.queryWithPermissionsAndLabels(id), HttpStatus.OK);
    }

    /**
     * 根据角色code查询角色
     *
     * @return 查询结果
     */
    @Permission(permissionWithin = true)
    @ApiOperation(value = "通过code查询角色")
    @GetMapping
    public ResponseEntity<RoleDTO> queryByCode(@RequestParam String code) {
        return new ResponseEntity<>(roleService.queryByCode(code), HttpStatus.OK);
    }

    /**
     * 根据角色code查询角色Id
     *
     * @return 查询结果
     */
    @Permission(permissionWithin = true)
    @ApiOperation(value = "通过code查询角色Id")
    @GetMapping("/idByCode")
    public ResponseEntity<Long> queryIdByCode(@RequestParam String code) {
        RoleDTO dto = roleService.queryByCode(code);
        Long roleId = dto == null ? null : dto.getId();
        return new ResponseEntity<>(roleId, HttpStatus.OK);
    }

    @Permission(type = ResourceType.SITE)
    @ApiOperation(value = "创建角色")
    @PostMapping
    public ResponseEntity<RoleDTO> create(@RequestBody @Validated RoleDTO roleDTO) {
        return new ResponseEntity<>(roleService.create(roleDTO), HttpStatus.OK);
    }

    @Permission(type = ResourceType.SITE)
    @ApiOperation(value = "基于已有角色创建角色")
    @PostMapping("/base_on_roles")
    public ResponseEntity<RoleDTO> createBaseOnRoles(@RequestBody @Validated RoleDTO roleDTO) {
        return new ResponseEntity<>(roleService.createBaseOnRoles(roleDTO), HttpStatus.OK);
    }

    @Permission(type = ResourceType.SITE)
    @ApiOperation(value = "修改角色")
    @PutMapping(value = "/{id}")
    public ResponseEntity<RoleDTO> update(@PathVariable Long id,
                                          @RequestBody RoleDTO roleDTO) {
        roleDTO.setId(id);
        return new ResponseEntity<>(roleService.update(roleDTO), HttpStatus.OK);
    }

    @Permission(type = ResourceType.SITE)
    @ApiOperation(value = "启用角色")
    @PutMapping(value = "/{id}/enable")
    public ResponseEntity<RoleDTO> enableRole(@PathVariable Long id) {
        return new ResponseEntity<>(roleService.enableRole(id), HttpStatus.OK);
    }

    @Permission(type = ResourceType.SITE)
    @ApiOperation(value = "禁用角色")
    @PutMapping(value = "/{id}/disable")
    public ResponseEntity<RoleDTO> disableRole(@PathVariable Long id) {
        return new ResponseEntity<>(roleService.disableRole(id), HttpStatus.OK);
    }

    @Permission(type = ResourceType.SITE)
    @ApiOperation(value = "角色信息校验")
    @PostMapping(value = "/check")
    public ResponseEntity check(@RequestBody RoleDTO role) {
        roleService.check(role);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Permission(type = ResourceType.SITE, permissionLogin = true)
    @ApiOperation("根据角色id查看角色对应的权限")
    @GetMapping("/{id}/permissions")
    @CustomPageRequest
    public ResponseEntity<PageInfo<PermissionDTO>> listPermissionById(@ApiIgnore
                                                                      @SortDefault(value = "id", direction = Sort.Direction.DESC) PageRequest pageRequest,
                                                                      @PathVariable("id") Long id,
                                                                      @RequestParam(value = "params", required = false) String[] params) {
        return new ResponseEntity<>(permissionService.listPermissionsByRoleId(pageRequest, id, ParamUtils.arrToStr(params)), HttpStatus.OK);
    }

    /**
     * 根据标签查询角色
     *
     * @return 查询结果
     */
    @Permission(type = ResourceType.SITE)
    @ApiOperation(value = "根据标签查询角色")
    @GetMapping(value = "/selectByLabel")
    public ResponseEntity<List<RoleDTO>> selectByLabel(@RequestParam String label) {
        return ResponseEntity.ok(roleService.selectByLabel(label, null));
    }
}
