package com.bochat.app.model.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.bochat.app.model.greendao.DaoSession;
import com.bochat.app.model.greendao.GroupEntityDao;
import com.bochat.app.model.greendao.GroupManagerEntityDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.io.Serializable;
import java.util.List;

/**
 * 2019/4/15
 * Author LDL
 **/
@Entity(
        nameInDb = "table_group"
)
public class GroupEntity extends CodeEntity implements Serializable {


    @Id
    @JSONField(name = "group_id")
    private long group_id; 

    private String create_time;
    private String group_head;
    private String group_introduce;
    private String group_label;
    private String group_level;
    private String group_name;
    private String level_num;
    private int people;
    private int member_num;
    private int role;
    private int joinMethod;
    private int status;
    private String question;
    
    @ToMany(referencedJoinProperty = "parentId")
    private List<GroupManagerEntity> managers;

    /** Used for active entity operations. */
    @Generated(hash = 1615357719)
    private transient GroupEntityDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;


    @Generated(hash = 455227852)
    public GroupEntity(long group_id, String create_time, String group_head, String group_introduce,
            String group_label, String group_level, String group_name, String level_num, int people,
            int member_num, int role, int joinMethod, int status, String question) {
        this.group_id = group_id;
        this.create_time = create_time;
        this.group_head = group_head;
        this.group_introduce = group_introduce;
        this.group_label = group_label;
        this.group_level = group_level;
        this.group_name = group_name;
        this.level_num = level_num;
        this.people = people;
        this.member_num = member_num;
        this.role = role;
        this.joinMethod = joinMethod;
        this.status = status;
        this.question = question;
    }

    @Generated(hash = 954040478)
    public GroupEntity() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getJoinMethod() {
        return joinMethod;
    }

    public void setJoinMethod(int joinMethod) {
        this.joinMethod = joinMethod;
    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getGroup_head() {
        return group_head;
    }

    public void setGroup_head(String group_head) {
        this.group_head = group_head;
    }

    public String getGroup_introduce() {
        return group_introduce;
    }

    public void setGroup_introduce(String group_introduce) {
        this.group_introduce = group_introduce;
    }

    public String getGroup_label() {
        return group_label;
    }

    public void setGroup_label(String group_label) {
        this.group_label = group_label;
    }

    public String getGroup_level() {
        return group_level;
    }

    public void setGroup_level(String group_level) {
        this.group_level = group_level;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getLevel_num() {
        return level_num;
    }

    public void setLevel_num(String level_num) {
        this.level_num = level_num;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getMember_num() {
        return member_num;
    }

    public void setMember_num(int member_num) {
        this.member_num = member_num;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GroupEntity{" +
                "group_id=" + group_id +
                ", create_time='" + create_time + '\'' +
                ", group_head='" + group_head + '\'' +
                ", group_introduce='" + group_introduce + '\'' +
                ", group_label='" + group_label + '\'' +
                ", group_level='" + group_level + '\'' +
                ", group_name='" + group_name + '\'' +
                ", level_num='" + level_num + '\'' +
                ", people=" + people +
                ", member_num=" + member_num +
                ", role=" + role +
                ", joinMethod=" + joinMethod +
                ", status=" + status +
                ", managers=" + managers +
                ", myDao=" + myDao +
                ", daoSession=" + daoSession +
                '}';
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1414571368)
    public synchronized void resetManagers() {
        managers = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1776595031)
    public List<GroupManagerEntity> getManagers() {
        if (managers == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GroupManagerEntityDao targetDao = daoSession.getGroupManagerEntityDao();
            List<GroupManagerEntity> managersNew = targetDao._queryGroupEntity_Managers(group_id);
            synchronized (this) {
                if(managers == null) {
                    managers = managersNew;
                }
            }
        }
        return managers;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1578846137)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getGroupEntityDao() : null;
    }
}
