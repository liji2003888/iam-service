package script.db

databaseChangeLog(logicalFilePath: 'script/db/iam_upload_history.groovy') {
    changeSet(author: 'superleader8@gmail.com', id: '2018-08-08-iam-upload-history') {
        createTable(tableName: "iam_upload_history") {
            column(name: 'id', type: 'BIGINT UNSIGNED', autoIncrement: true, remarks: '表ID，主键，供其他表做外键，unsigned bigint、单表时自增、步长为 1') {
                constraints(primaryKey: true)
            }
            column(name: 'user_id', type: 'BIGINT UNSIGNED', remarks: '用户id') {
                constraints(nullable: false)
            }
            column(name: 'url', type: 'VARCHAR(256)', remarks: '从文件服务下载的url地址')
            column(name: 'type', type: 'VARCHAR(32)', remarks: '上传的类型') {
                constraints(nullable: false)
            }
            column(name: 'success_count', type: "INTEGER UNSIGNED", remarks: '导入成功的数量')
            column(name: 'fail_count', type: "INTEGER UNSIGNED", remarks: '导入失败的数量')

            column(name: "object_version_number", type: "BIGINT UNSIGNED", defaultValue: "1") {
                constraints(nullable: true)
            }
            column(name: "created_by", type: "BIGINT UNSIGNED", defaultValue: "0") {
                constraints(nullable: true)
            }
            column(name: "creation_date", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "last_updated_by", type: "BIGINT UNSIGNED", defaultValue: "0") {
                constraints(nullable: true)
            }
            column(name: "last_update_date", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
        }
    }
}