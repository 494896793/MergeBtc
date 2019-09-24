package com.bochat.app.common.router;

import com.bochat.app.model.bean.ProjectIdentificationEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/19 16:59
 * Description :
 */

public class RouterDynamicProjectIdentificationResult extends AbstractRouter{
    public static final String PATH ="/path/RouterDynamicProjectIdentificationResult";

    private ProjectIdentificationEntity projectIdentificationEntity;

    public RouterDynamicProjectIdentificationResult(ProjectIdentificationEntity projectIdentificationEntity) {
        this.projectIdentificationEntity = projectIdentificationEntity;
    }

    public ProjectIdentificationEntity getProjectIdentificationEntity() {
        return projectIdentificationEntity;
    }

    public void setProjectIdentificationEntity(ProjectIdentificationEntity projectIdentificationEntity) {
        this.projectIdentificationEntity = projectIdentificationEntity;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
