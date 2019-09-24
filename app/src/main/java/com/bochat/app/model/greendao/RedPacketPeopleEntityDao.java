package com.bochat.app.model.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bochat.app.model.bean.RedPacketPeopleEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "table_redpacket_people".
*/
public class RedPacketPeopleEntityDao extends AbstractDao<RedPacketPeopleEntity, Long> {

    public static final String TABLENAME = "table_redpacket_people";

    /**
     * Properties of entity RedPacketPeopleEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Reward_id = new Property(0, long.class, "reward_id", true, "_id");
        public final static Property Receiver_id = new Property(1, long.class, "receiver_id", false, "RECEIVER_ID");
        public final static Property Receiver_name = new Property(2, String.class, "receiver_name", false, "RECEIVER_NAME");
        public final static Property Receive_money = new Property(3, double.class, "receive_money", false, "RECEIVE_MONEY");
    };


    public RedPacketPeopleEntityDao(DaoConfig config) {
        super(config);
    }
    
    public RedPacketPeopleEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"table_redpacket_people\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: reward_id
                "\"RECEIVER_ID\" INTEGER NOT NULL ," + // 1: receiver_id
                "\"RECEIVER_NAME\" TEXT," + // 2: receiver_name
                "\"RECEIVE_MONEY\" REAL NOT NULL );"); // 3: receive_money
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"table_redpacket_people\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, RedPacketPeopleEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getReward_id());
        stmt.bindLong(2, entity.getReceiver_id());
 
        String receiver_name = entity.getReceiver_name();
        if (receiver_name != null) {
            stmt.bindString(3, receiver_name);
        }
        stmt.bindDouble(4, entity.getReceive_money());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, RedPacketPeopleEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getReward_id());
        stmt.bindLong(2, entity.getReceiver_id());
 
        String receiver_name = entity.getReceiver_name();
        if (receiver_name != null) {
            stmt.bindString(3, receiver_name);
        }
        stmt.bindDouble(4, entity.getReceive_money());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public RedPacketPeopleEntity readEntity(Cursor cursor, int offset) {
        RedPacketPeopleEntity entity = new RedPacketPeopleEntity( //
            cursor.getLong(offset + 0), // reward_id
            cursor.getLong(offset + 1), // receiver_id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // receiver_name
            cursor.getDouble(offset + 3) // receive_money
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, RedPacketPeopleEntity entity, int offset) {
        entity.setReward_id(cursor.getLong(offset + 0));
        entity.setReceiver_id(cursor.getLong(offset + 1));
        entity.setReceiver_name(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setReceive_money(cursor.getDouble(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(RedPacketPeopleEntity entity, long rowId) {
        entity.setReward_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(RedPacketPeopleEntity entity) {
        if(entity != null) {
            return entity.getReward_id();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}