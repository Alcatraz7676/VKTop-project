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
public class PostSortItemRealmProxy extends com.ovchinnikovm.android.vktop.entities.PostSortItem
    implements RealmObjectProxy, PostSortItemRealmProxyInterface {

    static final class PostSortItemColumnInfo extends ColumnInfo {
        long idIndex;
        long likesIndex;
        long repostsIndex;
        long commentsIndex;
        long dateIndex;

        PostSortItemColumnInfo(OsSchemaInfo schemaInfo) {
            super(5);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("PostSortItem");
            this.idIndex = addColumnDetails("id", objectSchemaInfo);
            this.likesIndex = addColumnDetails("likes", objectSchemaInfo);
            this.repostsIndex = addColumnDetails("reposts", objectSchemaInfo);
            this.commentsIndex = addColumnDetails("comments", objectSchemaInfo);
            this.dateIndex = addColumnDetails("date", objectSchemaInfo);
        }

        PostSortItemColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new PostSortItemColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final PostSortItemColumnInfo src = (PostSortItemColumnInfo) rawSrc;
            final PostSortItemColumnInfo dst = (PostSortItemColumnInfo) rawDst;
            dst.idIndex = src.idIndex;
            dst.likesIndex = src.likesIndex;
            dst.repostsIndex = src.repostsIndex;
            dst.commentsIndex = src.commentsIndex;
            dst.dateIndex = src.dateIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(5);
        fieldNames.add("id");
        fieldNames.add("likes");
        fieldNames.add("reposts");
        fieldNames.add("comments");
        fieldNames.add("date");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private PostSortItemColumnInfo columnInfo;
    private ProxyState<com.ovchinnikovm.android.vktop.entities.PostSortItem> proxyState;

    PostSortItemRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (PostSortItemColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.ovchinnikovm.android.vktop.entities.PostSortItem>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public Integer realmGet$id() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.idIndex)) {
            return null;
        }
        return (int) proxyState.getRow$realm().getLong(columnInfo.idIndex);
    }

    @Override
    public void realmSet$id(Integer value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.idIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(columnInfo.idIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.idIndex);
            return;
        }
        proxyState.getRow$realm().setLong(columnInfo.idIndex, value);
    }

    @Override
    public com.ovchinnikovm.android.vktop.entities.SocialValue realmGet$likes() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.likesIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.ovchinnikovm.android.vktop.entities.SocialValue.class, proxyState.getRow$realm().getLink(columnInfo.likesIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$likes(com.ovchinnikovm.android.vktop.entities.SocialValue value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("likes")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.likesIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.likesIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.likesIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.likesIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    public com.ovchinnikovm.android.vktop.entities.SocialValue realmGet$reposts() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.repostsIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.ovchinnikovm.android.vktop.entities.SocialValue.class, proxyState.getRow$realm().getLink(columnInfo.repostsIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$reposts(com.ovchinnikovm.android.vktop.entities.SocialValue value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("reposts")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.repostsIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.repostsIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.repostsIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.repostsIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    public com.ovchinnikovm.android.vktop.entities.SocialValue realmGet$comments() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.commentsIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.ovchinnikovm.android.vktop.entities.SocialValue.class, proxyState.getRow$realm().getLink(columnInfo.commentsIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$comments(com.ovchinnikovm.android.vktop.entities.SocialValue value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("comments")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.commentsIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.commentsIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.commentsIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.commentsIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    @SuppressWarnings("cast")
    public Integer realmGet$date() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.dateIndex)) {
            return null;
        }
        return (int) proxyState.getRow$realm().getLong(columnInfo.dateIndex);
    }

    @Override
    public void realmSet$date(Integer value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dateIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setLong(columnInfo.dateIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dateIndex);
            return;
        }
        proxyState.getRow$realm().setLong(columnInfo.dateIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("PostSortItem", 5, 0);
        builder.addPersistedProperty("id", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("likes", RealmFieldType.OBJECT, "SocialValue");
        builder.addPersistedLinkProperty("reposts", RealmFieldType.OBJECT, "SocialValue");
        builder.addPersistedLinkProperty("comments", RealmFieldType.OBJECT, "SocialValue");
        builder.addPersistedProperty("date", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static PostSortItemColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new PostSortItemColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "PostSortItem";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.ovchinnikovm.android.vktop.entities.PostSortItem createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(3);
        if (json.has("likes")) {
            excludeFields.add("likes");
        }
        if (json.has("reposts")) {
            excludeFields.add("reposts");
        }
        if (json.has("comments")) {
            excludeFields.add("comments");
        }
        com.ovchinnikovm.android.vktop.entities.PostSortItem obj = realm.createObjectInternal(com.ovchinnikovm.android.vktop.entities.PostSortItem.class, true, excludeFields);

        final PostSortItemRealmProxyInterface objProxy = (PostSortItemRealmProxyInterface) obj;
        if (json.has("id")) {
            if (json.isNull("id")) {
                objProxy.realmSet$id(null);
            } else {
                objProxy.realmSet$id((int) json.getInt("id"));
            }
        }
        if (json.has("likes")) {
            if (json.isNull("likes")) {
                objProxy.realmSet$likes(null);
            } else {
                com.ovchinnikovm.android.vktop.entities.SocialValue likesObj = SocialValueRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("likes"), update);
                objProxy.realmSet$likes(likesObj);
            }
        }
        if (json.has("reposts")) {
            if (json.isNull("reposts")) {
                objProxy.realmSet$reposts(null);
            } else {
                com.ovchinnikovm.android.vktop.entities.SocialValue repostsObj = SocialValueRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("reposts"), update);
                objProxy.realmSet$reposts(repostsObj);
            }
        }
        if (json.has("comments")) {
            if (json.isNull("comments")) {
                objProxy.realmSet$comments(null);
            } else {
                com.ovchinnikovm.android.vktop.entities.SocialValue commentsObj = SocialValueRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("comments"), update);
                objProxy.realmSet$comments(commentsObj);
            }
        }
        if (json.has("date")) {
            if (json.isNull("date")) {
                objProxy.realmSet$date(null);
            } else {
                objProxy.realmSet$date((int) json.getInt("date"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.ovchinnikovm.android.vktop.entities.PostSortItem createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.ovchinnikovm.android.vktop.entities.PostSortItem obj = new com.ovchinnikovm.android.vktop.entities.PostSortItem();
        final PostSortItemRealmProxyInterface objProxy = (PostSortItemRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$id((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$id(null);
                }
            } else if (name.equals("likes")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$likes(null);
                } else {
                    com.ovchinnikovm.android.vktop.entities.SocialValue likesObj = SocialValueRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$likes(likesObj);
                }
            } else if (name.equals("reposts")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$reposts(null);
                } else {
                    com.ovchinnikovm.android.vktop.entities.SocialValue repostsObj = SocialValueRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$reposts(repostsObj);
                }
            } else if (name.equals("comments")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$comments(null);
                } else {
                    com.ovchinnikovm.android.vktop.entities.SocialValue commentsObj = SocialValueRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$comments(commentsObj);
                }
            } else if (name.equals("date")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$date((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$date(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    public static com.ovchinnikovm.android.vktop.entities.PostSortItem copyOrUpdate(Realm realm, com.ovchinnikovm.android.vktop.entities.PostSortItem object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.ovchinnikovm.android.vktop.entities.PostSortItem) cachedRealmObject;
        }

        return copy(realm, object, update, cache);
    }

    public static com.ovchinnikovm.android.vktop.entities.PostSortItem copy(Realm realm, com.ovchinnikovm.android.vktop.entities.PostSortItem newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.ovchinnikovm.android.vktop.entities.PostSortItem) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.ovchinnikovm.android.vktop.entities.PostSortItem realmObject = realm.createObjectInternal(com.ovchinnikovm.android.vktop.entities.PostSortItem.class, false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        PostSortItemRealmProxyInterface realmObjectSource = (PostSortItemRealmProxyInterface) newObject;
        PostSortItemRealmProxyInterface realmObjectCopy = (PostSortItemRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$id(realmObjectSource.realmGet$id());

        com.ovchinnikovm.android.vktop.entities.SocialValue likesObj = realmObjectSource.realmGet$likes();
        if (likesObj == null) {
            realmObjectCopy.realmSet$likes(null);
        } else {
            com.ovchinnikovm.android.vktop.entities.SocialValue cachelikes = (com.ovchinnikovm.android.vktop.entities.SocialValue) cache.get(likesObj);
            if (cachelikes != null) {
                realmObjectCopy.realmSet$likes(cachelikes);
            } else {
                realmObjectCopy.realmSet$likes(SocialValueRealmProxy.copyOrUpdate(realm, likesObj, update, cache));
            }
        }

        com.ovchinnikovm.android.vktop.entities.SocialValue repostsObj = realmObjectSource.realmGet$reposts();
        if (repostsObj == null) {
            realmObjectCopy.realmSet$reposts(null);
        } else {
            com.ovchinnikovm.android.vktop.entities.SocialValue cachereposts = (com.ovchinnikovm.android.vktop.entities.SocialValue) cache.get(repostsObj);
            if (cachereposts != null) {
                realmObjectCopy.realmSet$reposts(cachereposts);
            } else {
                realmObjectCopy.realmSet$reposts(SocialValueRealmProxy.copyOrUpdate(realm, repostsObj, update, cache));
            }
        }

        com.ovchinnikovm.android.vktop.entities.SocialValue commentsObj = realmObjectSource.realmGet$comments();
        if (commentsObj == null) {
            realmObjectCopy.realmSet$comments(null);
        } else {
            com.ovchinnikovm.android.vktop.entities.SocialValue cachecomments = (com.ovchinnikovm.android.vktop.entities.SocialValue) cache.get(commentsObj);
            if (cachecomments != null) {
                realmObjectCopy.realmSet$comments(cachecomments);
            } else {
                realmObjectCopy.realmSet$comments(SocialValueRealmProxy.copyOrUpdate(realm, commentsObj, update, cache));
            }
        }
        realmObjectCopy.realmSet$date(realmObjectSource.realmGet$date());
        return realmObject;
    }

    public static long insert(Realm realm, com.ovchinnikovm.android.vktop.entities.PostSortItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.ovchinnikovm.android.vktop.entities.PostSortItem.class);
        long tableNativePtr = table.getNativePtr();
        PostSortItemColumnInfo columnInfo = (PostSortItemColumnInfo) realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.PostSortItem.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        Number realmGet$id = ((PostSortItemRealmProxyInterface) object).realmGet$id();
        if (realmGet$id != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id.longValue(), false);
        }

        com.ovchinnikovm.android.vktop.entities.SocialValue likesObj = ((PostSortItemRealmProxyInterface) object).realmGet$likes();
        if (likesObj != null) {
            Long cachelikes = cache.get(likesObj);
            if (cachelikes == null) {
                cachelikes = SocialValueRealmProxy.insert(realm, likesObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.likesIndex, rowIndex, cachelikes, false);
        }

        com.ovchinnikovm.android.vktop.entities.SocialValue repostsObj = ((PostSortItemRealmProxyInterface) object).realmGet$reposts();
        if (repostsObj != null) {
            Long cachereposts = cache.get(repostsObj);
            if (cachereposts == null) {
                cachereposts = SocialValueRealmProxy.insert(realm, repostsObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.repostsIndex, rowIndex, cachereposts, false);
        }

        com.ovchinnikovm.android.vktop.entities.SocialValue commentsObj = ((PostSortItemRealmProxyInterface) object).realmGet$comments();
        if (commentsObj != null) {
            Long cachecomments = cache.get(commentsObj);
            if (cachecomments == null) {
                cachecomments = SocialValueRealmProxy.insert(realm, commentsObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.commentsIndex, rowIndex, cachecomments, false);
        }
        Number realmGet$date = ((PostSortItemRealmProxyInterface) object).realmGet$date();
        if (realmGet$date != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date.longValue(), false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.ovchinnikovm.android.vktop.entities.PostSortItem.class);
        long tableNativePtr = table.getNativePtr();
        PostSortItemColumnInfo columnInfo = (PostSortItemColumnInfo) realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.PostSortItem.class);
        com.ovchinnikovm.android.vktop.entities.PostSortItem object = null;
        while (objects.hasNext()) {
            object = (com.ovchinnikovm.android.vktop.entities.PostSortItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            Number realmGet$id = ((PostSortItemRealmProxyInterface) object).realmGet$id();
            if (realmGet$id != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id.longValue(), false);
            }

            com.ovchinnikovm.android.vktop.entities.SocialValue likesObj = ((PostSortItemRealmProxyInterface) object).realmGet$likes();
            if (likesObj != null) {
                Long cachelikes = cache.get(likesObj);
                if (cachelikes == null) {
                    cachelikes = SocialValueRealmProxy.insert(realm, likesObj, cache);
                }
                table.setLink(columnInfo.likesIndex, rowIndex, cachelikes, false);
            }

            com.ovchinnikovm.android.vktop.entities.SocialValue repostsObj = ((PostSortItemRealmProxyInterface) object).realmGet$reposts();
            if (repostsObj != null) {
                Long cachereposts = cache.get(repostsObj);
                if (cachereposts == null) {
                    cachereposts = SocialValueRealmProxy.insert(realm, repostsObj, cache);
                }
                table.setLink(columnInfo.repostsIndex, rowIndex, cachereposts, false);
            }

            com.ovchinnikovm.android.vktop.entities.SocialValue commentsObj = ((PostSortItemRealmProxyInterface) object).realmGet$comments();
            if (commentsObj != null) {
                Long cachecomments = cache.get(commentsObj);
                if (cachecomments == null) {
                    cachecomments = SocialValueRealmProxy.insert(realm, commentsObj, cache);
                }
                table.setLink(columnInfo.commentsIndex, rowIndex, cachecomments, false);
            }
            Number realmGet$date = ((PostSortItemRealmProxyInterface) object).realmGet$date();
            if (realmGet$date != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date.longValue(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.ovchinnikovm.android.vktop.entities.PostSortItem object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.ovchinnikovm.android.vktop.entities.PostSortItem.class);
        long tableNativePtr = table.getNativePtr();
        PostSortItemColumnInfo columnInfo = (PostSortItemColumnInfo) realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.PostSortItem.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        Number realmGet$id = ((PostSortItemRealmProxyInterface) object).realmGet$id();
        if (realmGet$id != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id.longValue(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.idIndex, rowIndex, false);
        }

        com.ovchinnikovm.android.vktop.entities.SocialValue likesObj = ((PostSortItemRealmProxyInterface) object).realmGet$likes();
        if (likesObj != null) {
            Long cachelikes = cache.get(likesObj);
            if (cachelikes == null) {
                cachelikes = SocialValueRealmProxy.insertOrUpdate(realm, likesObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.likesIndex, rowIndex, cachelikes, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.likesIndex, rowIndex);
        }

        com.ovchinnikovm.android.vktop.entities.SocialValue repostsObj = ((PostSortItemRealmProxyInterface) object).realmGet$reposts();
        if (repostsObj != null) {
            Long cachereposts = cache.get(repostsObj);
            if (cachereposts == null) {
                cachereposts = SocialValueRealmProxy.insertOrUpdate(realm, repostsObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.repostsIndex, rowIndex, cachereposts, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.repostsIndex, rowIndex);
        }

        com.ovchinnikovm.android.vktop.entities.SocialValue commentsObj = ((PostSortItemRealmProxyInterface) object).realmGet$comments();
        if (commentsObj != null) {
            Long cachecomments = cache.get(commentsObj);
            if (cachecomments == null) {
                cachecomments = SocialValueRealmProxy.insertOrUpdate(realm, commentsObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.commentsIndex, rowIndex, cachecomments, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.commentsIndex, rowIndex);
        }
        Number realmGet$date = ((PostSortItemRealmProxyInterface) object).realmGet$date();
        if (realmGet$date != null) {
            Table.nativeSetLong(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date.longValue(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dateIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.ovchinnikovm.android.vktop.entities.PostSortItem.class);
        long tableNativePtr = table.getNativePtr();
        PostSortItemColumnInfo columnInfo = (PostSortItemColumnInfo) realm.getSchema().getColumnInfo(com.ovchinnikovm.android.vktop.entities.PostSortItem.class);
        com.ovchinnikovm.android.vktop.entities.PostSortItem object = null;
        while (objects.hasNext()) {
            object = (com.ovchinnikovm.android.vktop.entities.PostSortItem) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            Number realmGet$id = ((PostSortItemRealmProxyInterface) object).realmGet$id();
            if (realmGet$id != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id.longValue(), false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.idIndex, rowIndex, false);
            }

            com.ovchinnikovm.android.vktop.entities.SocialValue likesObj = ((PostSortItemRealmProxyInterface) object).realmGet$likes();
            if (likesObj != null) {
                Long cachelikes = cache.get(likesObj);
                if (cachelikes == null) {
                    cachelikes = SocialValueRealmProxy.insertOrUpdate(realm, likesObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.likesIndex, rowIndex, cachelikes, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.likesIndex, rowIndex);
            }

            com.ovchinnikovm.android.vktop.entities.SocialValue repostsObj = ((PostSortItemRealmProxyInterface) object).realmGet$reposts();
            if (repostsObj != null) {
                Long cachereposts = cache.get(repostsObj);
                if (cachereposts == null) {
                    cachereposts = SocialValueRealmProxy.insertOrUpdate(realm, repostsObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.repostsIndex, rowIndex, cachereposts, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.repostsIndex, rowIndex);
            }

            com.ovchinnikovm.android.vktop.entities.SocialValue commentsObj = ((PostSortItemRealmProxyInterface) object).realmGet$comments();
            if (commentsObj != null) {
                Long cachecomments = cache.get(commentsObj);
                if (cachecomments == null) {
                    cachecomments = SocialValueRealmProxy.insertOrUpdate(realm, commentsObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.commentsIndex, rowIndex, cachecomments, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.commentsIndex, rowIndex);
            }
            Number realmGet$date = ((PostSortItemRealmProxyInterface) object).realmGet$date();
            if (realmGet$date != null) {
                Table.nativeSetLong(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date.longValue(), false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dateIndex, rowIndex, false);
            }
        }
    }

    public static com.ovchinnikovm.android.vktop.entities.PostSortItem createDetachedCopy(com.ovchinnikovm.android.vktop.entities.PostSortItem realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.ovchinnikovm.android.vktop.entities.PostSortItem unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.ovchinnikovm.android.vktop.entities.PostSortItem();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.ovchinnikovm.android.vktop.entities.PostSortItem) cachedObject.object;
            }
            unmanagedObject = (com.ovchinnikovm.android.vktop.entities.PostSortItem) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        PostSortItemRealmProxyInterface unmanagedCopy = (PostSortItemRealmProxyInterface) unmanagedObject;
        PostSortItemRealmProxyInterface realmSource = (PostSortItemRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$id(realmSource.realmGet$id());

        // Deep copy of likes
        unmanagedCopy.realmSet$likes(SocialValueRealmProxy.createDetachedCopy(realmSource.realmGet$likes(), currentDepth + 1, maxDepth, cache));

        // Deep copy of reposts
        unmanagedCopy.realmSet$reposts(SocialValueRealmProxy.createDetachedCopy(realmSource.realmGet$reposts(), currentDepth + 1, maxDepth, cache));

        // Deep copy of comments
        unmanagedCopy.realmSet$comments(SocialValueRealmProxy.createDetachedCopy(realmSource.realmGet$comments(), currentDepth + 1, maxDepth, cache));
        unmanagedCopy.realmSet$date(realmSource.realmGet$date());

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
        PostSortItemRealmProxy aPostSortItem = (PostSortItemRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aPostSortItem.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aPostSortItem.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aPostSortItem.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
