package net.suool.model;

/**
 * 服务端XML解析数据实体封装器
 * Created by SuooL on 2014/11/30 0030.
 */
public class UpdateInfo {
    private String version;  // 服务端版本号
    private String description; // 服务端升级提示信息
    private String apkUrl; // 服务端新版本APK的下载地址

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }
}
