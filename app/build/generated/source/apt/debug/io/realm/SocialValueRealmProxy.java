package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.ProxyUtils;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsList;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.Property;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class SocialValueRealmProxy extends com.ovchinnikovm.android.vktop.entities.SocialValue
    implements RealmObjectProxy, SocialValueRealmProxyInterface {

    static final class SocialValueColumnInfo extends ColumnInfo {
        long countIndex;

        SocialValueColumnInfo(OsSchemaInfo schemaInfo) {
            super(1);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("SocialValue");
            this.countIndex = addColumnDetails("count", objectSchemaInfo);
        }

        SocialValueColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new SocialValueColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final SocialValueColumnInfo src = (SocialValueColumnInfo) rawSrc;
            final SocialValueColumnInfo dst = (SocialValueColumnInfo) rawDst;
            dst.countIndex = src.countIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(1);
        fieldNames.add("count");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private SocialValueColumnInfo columnInfo;
    private ProxyState<com.ovchinnikovm.android.vktop.entities.SocialValue> proxyState;

    SocialValueRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (SocialValueColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.ovchinnikovm.android.vktop.entities.SocialValue>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public Integer realmGet$count() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.countIndex)) {
            return null;
        }
        return (int) proxyState.getRow$realm().getLong(columnInfo.countIndex);
    }

    @Override
    public void realmSet$count(Integer value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.countIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(columnInfo.countIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.countIndex);
            return;
        }
        proxyState.getRow$realm().setLong(columnInfo.countIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("SocialValue", 1, 0);
        builder.addPersistedProperty("count", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static SocialValueColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new SocialValueColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "SocialValue";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.ovchinnikovm.android.vktop.entities.SocialValue createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.ovchinnikovm.android.vktop.entities.SocialValue obj = realm.createObjectInternal(com.ovchinnikovm.android.vktop.entities.SocialValue.class, true, excludeFields);

        final SocialValueRealmProxyInterface objProxy = (SocialValueRealmProxyInterface) obj;
        if (json.has("count")) {
            if (json.isNull("count")) {
                objProxy.realmSet$count(null);
            } else {
                objProxy.realmSet$count((int) json.getInt("count"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.ovchinnikovm.android.vktop.entities.SocialValue createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.ovchinnikovm.android.vktop.entities.SocialValue obj = new com.ovchinnikovm.android.vktop.entities.SocialValue();
        final SocialValueRealmProxyInterface objProxy = (SocialValueRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("count")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$count((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$count(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    public static com.ovchinnikovm.android.vktop.entities.SocialValue copyOrUpdate(Realm realm, com.ovchinnikovm.android.vktop.entities.SocialValue object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.ovchinnikovm.android.vktop.entities.SocialValue) cachedRealmObject;
        }

        return copy(realm, object, update, cache);
    }

    public static com.ovchinnikovm.android.vktop.entities.SocialValue copy(Realm realm, com.ovchinnikovm.android.vktop.entities.SocialValue newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.ovchinnikovm.android.vktop.entities.SocialValue) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.ovchinnikovm.android.vktop.entities.SocialValue realmObject = realm.createObjectInternal(com.ovchinnikovm.android.vktop.entities.SocialValue.class, false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        SocialValueRealmProxyInterface realmObjectSource = (SocialValueRealmProxyInterface) newObject;
        SocialValueRealmProxyInterface realmObjectCopy = (SocialValueRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$count(realmObjectSource.realmGet$count());
        return realmObject;
    }

    public static long insert(Realm realm, com.ovchinnikovm.android.vktop.entities.SocialValue object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.ovchinnikovm.android.vktop.entities.SocialValue.class);
        long tableNativePtr = table.getNativePtr();
        SocialValueColumnInfo columnInfo = (SocialValueColumnInfo) realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.SocialValue.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        Number realmGet$count = ((SocialValueRealmProxyInterface) object).realmGet$count();
        if (realmGet$count != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.countIndex, rowIndex, realmGet$count.longValue(), false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.ovchinnikovm.android.vktop.entities.SocialValue.class);
        long tableNativePtr = table.getNativePtr();
        SocialValueColumnInfo columnInfo = (SocialValueColumnInfo) realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.SocialValue.class);
        com.ovchinnikovm.android.vktop.entities.SocialValue object = null;
        while (objects.hasNext()) {
            object = (com.ovchinnikovm.android.vktop.entities.SocialValue) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            Number realmGet$count = ((SocialValueRealmProxyInterface) object).realmGet$count();
            if (realmGet$count != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.countIndex, rowIndex, realmGet$count.longValue(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.ovchinnikovm.android.vktop.entities.SocialValue object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.ovchinnikovm.android.vktop.entities.SocialValue.class);
        long tableNativePtr = table.getNativePtr();
        SocialValueColumnInfo columnInfo = (SocialValueColumnInfo) realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.SocialValue.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        Number realmGet$count = ((SocialValueRealmProxyInterface) object).realmGet$count();
        if (realmGet$count != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.countIndex, rowIndex, realmGet$count.longValue(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.countIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.ovchinnikovm.android.vktop.entities.SocialValue.class);
        long tableNativePtr = table.getNativePtr();
        SocialValueColumnInfo columnInfo = (SocialValueColumnInfo) realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.SocialValue.class);
        com.ovchinnikovm.android.vktop.entities.SocialValue object = null;
        while (objects.hasNext()) {
            object = (com.ovchinnikovm.android.vktop.entities.SocialValue) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            Number realmGet$count = ((SocialValueRealmProxyInterface) object).realmGet$count();
            if (realmGet$count != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.countIndex, rowIndex, realmGet$count.longValue(), false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.countIndex, rowIndex, false);
            }
        }
    }

    public static com.ovchinnikovm.android.vktop.entities.SocialValue createDetachedCopy(com.ovchinnikovm.android.vktop.entities.SocialValue realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.ovchinnikovm.android.vktop.entities.SocialValue unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.ovchinnikovm.android.vktop.entities.SocialValue();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.ovchinnikovm.android.vktop.entities.SocialValue) cachedObject.object;
            }
            unmanagedObject = (com.ovchinnikovm.android.vktop.entities.SocialValue) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        SocialValueRealmProxyInterface unmanagedCopy = (SocialValueRealmProxyInterface) unmanagedObject;
        SocialValueRealmProxyInterface realmSource = (SocialValueRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$count(realmSource.realmGet$count());

        return unmanagedObject;
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialValueRealmProxy aSocialValue = (SocialValueRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aSocialValue.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aSocialValue.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aSocialValue.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
