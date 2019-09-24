package com.bochat.app.model.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.bochat.app.model.bean.RedPacketStatuEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "table_redpackets".
*/
public class RedPacketStatuEntityDao extends AbstractDao<RedPacketStatuEntity, Long> {

    public static final String TABLENAME = "table_redpackets";

    /**
     * Properties of entity RedPacketStatuEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Status = new Property(1, int.class, "status", false, "STATUS");
        public final static Property Count = new Property(2, double.class, "count", false, "COUNT");
        public final static Property ReadyGet = new Property(3, int.class, "readyGet", false, "READY_GET");
        public final static Property BidName = new Property(4, String.class, "bidName", false, "BID_NAME");
        public final static Property Coin = new Property(5, String.class, "coin", false, "COIN");
    };


    public RedPacketStatuEntityDao(DaoConfig config) {
        super(config);
    }
    
    public RedPacketStatuEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"table_redpackets\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"STATUS\" INTEGER NOT NULL ," + // 1: status
                "\"COUNT\" REAL NOT NULL ," + // 2: count
                "\"READY_GET\" INTEGER NOT NULL ," + // 3: readyGet
                "\"BID_NAME\" TEXT," + // 4: bidName
                "\"COIN\" TEXT);"); // 5: coin
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"table_redpackets\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, RedPacketStatuEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getStatus());
        stmt.bindDouble(3, entity.getCount());
        stmt.bindLong(4, entity.getReadyGet());
 
        String bidName = entity.getBidName();
        if (bidName != null) {
            stmt.bindString(5, bidName);
        }
 
        String coin = entity.getCoin();
        if (coin != null) {
            stmt.bindString(6, coin);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, RedPacketStatuEntity entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getStatus());
        stmt.bindDouble(3, entity.getCount());
        stmt.bindLong(4, entity.getReadyGet());
 
        String bidName = entity.getBidName();
        if (bidName != null) {
            stmt.bindString(5, bidName);
        }
 
        String coin = entity.getCoin();
        if (coin != null) {
            stmt.bindString(6, coin);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public RedPacketStatuEntity readEntity(Cursor cursor, int offset) {
        RedPacketStatuEntity entity = new RedPacketStatuEntity( //
            cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // status
            cursor.getDouble(offset + 2), // count
            cursor.getInt(offset + 3), // readyGet
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // bidName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // coin
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, RedPacketStatuEntity entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setStatus(cursor.getInt(offset + 1));
        entity.setCount(cursor.getDouble(offset + 2));
        entity.setReadyGet(cursor.getInt(offset + 3));
        entity.setBidName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCoin(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(RedPacketStatuEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(RedPacketStatuEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
