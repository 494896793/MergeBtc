package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.ProtocolBookEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.io.File;

/**
 * 2019/5/24
 * Author LDL
 **/
public interface ProjectIdentificationContract {

    interface View extends IBaseView<ProjectIdentificationContract.Presenter>{
        void addProjectSuccess(CodeEntity codeEntity);
        void getProtocolBookSuccess(ProtocolBookEntity entity);
    }

    interface Presenter extends IBasePresenter<ProjectIdentificationContract.View>{
        void addProjectParty(String companyName, String website, final File logo, final File license);
        void getProtocolBook();
    }

}
