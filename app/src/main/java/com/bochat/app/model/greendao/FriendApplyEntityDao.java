package com.bochat.app.model.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bochat.app.model.bean.FriendApplyEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "table_apply".
*/
public class FriendApplyEntityDao extends AbstractDao<FriendApplyEntity, String> {

    public static final String TABLENAME = "table_apply";

    /**
     * Properties of entity FriendApplyEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Proposer_id = new Property(0, String.class, "proposer_id", true, "PROPOSER_ID");
        public final static Property Apply_time = new Property(1, String.class, "apply_time", false, "APPLY_TIME");
        public final static Property Apply_state = new Property(2, String.class, "apply_state", false, "APPLY_STATE");
        public final static Property Apply_text = new Property(3, String.class, "apply_text", false, "APPLY_TEXT");
        public final static Property Apply_from = new Property(4, String.class, "apply_from", false, "APPLY_FROM");
        public final static Property Nickname = new Property(5, String.class, "nickname", false, "NICKNAME");
        public final static Property Age = new Property(6, String.class, "age", false, "AGE");
        public final static Property Address = new Property(7, String.class, "address", false, "ADDRESS");
        public final static Property Head_img = new Property(8, String.class, "head_img", false, "HEAD_IMG");
        public final static Property IsRead = new Property(9, String.class, "isRead", false, "IS_READ");
        public final static Property SourceType = new Property(10, int.class, "SourceType", false, "SOURCE_TYPE");
        public final static Property Extra = new Property(11, String.class, "extra", false, "EXTRA");
        public final static Property Desc = new Property(12, String.class, "desc", false, "DESC");
    };


    public FriendApplyEntityDao(DaoConfig config) {
        super(config);
    }
    
    public FriendApplyEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"table_apply\" (" + //
                "\"PROPOSER_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: proposer_id
                "\"APPLY_TIME\" TEXT," + // 1: apply_time
                "\"APPLY_STATE\" TEXT," + // 2: apply_state
                "\"APPLY_TEXT\" TEXT," + // 3: apply_text
                "\"APPLY_FROM\" TEXT," + // 4: apply_from
                "\"NICKNAME\" TEXT," + // 5: nickname
                "\"AGE\" TEXT," + // 6: age
                "\"ADDRESS\" TEXT," + // 7: address
                "\"HEAD_IMG\" TEXT," + // 8: head_img
                "\"IS_READ\" TEXT," + // 9: isRead
                "\"SOURCE_TYPE\" INTEGER NOT NULL ," + // 10: SourceType
                "\"EXTRA\" TEXT," + // 11: extra
                "\"DESC\" TEXT);"); // 12: desc
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"table_apply\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FriendApplyEntity entity) {
        stmt.clearBindings();
 
        String proposer_id = entity.getProposer_id();
        if (proposer_id != null) {
            stmt.bindString(1, proposer_id);
        }
 
        String apply_time = entity.getApply_time();
        if (apply_time != null) {
            stmt.bindString(2, apply_time);
        }
 
        String apply_state = entity.getApply_state();
        if (apply_state != null) {
            stmt.bindString(3, apply_state);
        }
 
        String apply_text = entity.getApply_text();
        if (apply_text != null) {
            stmt.bindString(4, apply_text);
        }
 
        String apply_from = entity.getApply_from();
        if (apply_from != null) {
            stmt.bindString(5, apply_from);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(6, nickname);
        }
 
        String age = entity.getAge();
        if (age != null) {
            stmt.bindString(7, age);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(8, address);
        }
 
        String head_img = entity.getHead_img();
        if (head_img != null) {
            stmt.bindString(9, head_img);
        }
 
        String isRead = entity.getIsRead();
        if (isRead != null) {
            stmt.bindString(10, isRead);
        }
        stmt.bindLong(11, entity.getSourceType());
 
        String extra = entity.getExtra();
        if (extra != null) {
            stmt.bindString(12, extra);
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(13, desc);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, FriendApplyEntity entity) {
        stmt.clearBindings();
 
        String proposer_id = entity.getProposer_id();
        if (proposer_id != null) {
            stmt.bindString(1, proposer_id);
        }
 
        String apply_time = entity.getApply_time();
        if (apply_time != null) {
            stmt.bindString(2, apply_time);
        }
 
        String apply_state = entity.getApply_state();
        if (apply_state != null) {
            stmt.bindString(3, apply_state);
        }
 
        String apply_text = entity.getApply_text();
        if (apply_text != null) {
            stmt.bindString(4, apply_text);
        }
 
        String apply_from = entity.getApply_from();
        if (apply_from != null) {
            stmt.bindString(5, apply_from);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(6, nickname);
        }
 
        String age = entity.getAge();
        if (age != null) {
            stmt.bindString(7, age);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(8, address);
        }
 
        String head_img = entity.getHead_img();
        if (head_img != null) {
            stmt.bindString(9, head_img);
        }
 
        String isRead = entity.getIsRead();
        if (isRead != null) {
            stmt.bindString(10, isRead);
        }
        stmt.bindLong(11, entity.getSourceType());
 
        String extra = entity.getExtra();
        if (extra != null) {
            stmt.bindString(12, extra);
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(13, desc);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public FriendApplyEntity readEntity(Cursor cursor, int offset) {
        FriendApplyEntity entity = new FriendApplyEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // proposer_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // apply_time
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // apply_state
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // apply_text
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // apply_from
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // nickname
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // age
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // address
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // head_img
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // isRead
            cursor.getInt(offset + 10), // SourceType
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // extra
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12) // desc
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, FriendApplyEntity entity, int offset) {
        entity.setProposer_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setApply_time(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setApply_state(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setApply_text(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setApply_from(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setNickname(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setAge(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAddress(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setHead_img(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setIsRead(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setSourceType(cursor.getInt(offset + 10));
        entity.setExtra(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setDesc(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
     }
    
    @Override
    protected final String updateKeyAfterInsert(FriendApplyEntity entity, long rowId) {
        return entity.getProposer_id();
    }
    
    @Override
    public String getKey(FriendApplyEntity entity) {
        if(entity != null) {
            return entity.getProposer_id();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}