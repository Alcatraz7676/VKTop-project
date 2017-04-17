package io.realm;


import android.util.JsonReader;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@io.realm.annotations.RealmModule
class DefaultRealmModuleMediator extends RealmProxyMediator {

    private static final Set<Class<? extends RealmModel>> MODEL_CLASSES;
    static {
        Set<Class<? extends RealmModel>> modelClasses = new HashSet<Class<? extends RealmModel>>(3);
        modelClasses.add(com.ovchinnikovm.android.vktop.entities.PostSortItem.class);
        modelClasses.add(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class);
        modelClasses.add(com.ovchinnikovm.android.vktop.entities.SocialValue.class);
        MODEL_CLASSES = Collections.unmodifiableSet(modelClasses);
    }

    @Override
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        Map<Class<? extends RealmModel>, OsObjectSchemaInfo> infoMap = new HashMap<Class<? extends RealmModel>, OsObjectSchemaInfo>(3);
        infoMap.put(com.ovchinnikovm.android.vktop.entities.PostSortItem.class, io.realm.PostSortItemRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class, io.realm.RealmSortedItemRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.ovchinnikovm.android.vktop.entities.SocialValue.class, io.realm.SocialValueRealmProxy.getExpectedObjectSchemaInfo());
        return infoMap;
    }

    @Override
    public ColumnInfo createColumnInfo(Class<? extends RealmModel> clazz, OsSchemaInfo schemaInfo) {
        checkClass(clazz);

        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.PostSortItem.class)) {
            return io.realm.PostSortItemRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class)) {
            return io.realm.RealmSortedItemRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.SocialValue.class)) {
            return io.realm.SocialValueRealmProxy.createColumnInfo(schemaInfo);
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public List<String> getFieldNames(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.PostSortItem.class)) {
            return io.realm.PostSortItemRealmProxy.getFieldNames();
        }
        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class)) {
            return io.realm.RealmSortedItemRealmProxy.getFieldNames();
        }
        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.SocialValue.class)) {
            return io.realm.SocialValueRealmProxy.getFieldNames();
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public String getSimpleClassNameImpl(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.PostSortItem.class)) {
            return io.realm.PostSortItemRealmProxy.getSimpleClassName();
        }
        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class)) {
            return io.realm.RealmSortedItemRealmProxy.getSimpleClassName();
        }
        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.SocialValue.class)) {
            return io.realm.SocialValueRealmProxy.getSimpleClassName();
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E newInstance(Class<E> clazz, Object baseRealm, Row row, ColumnInfo columnInfo, boolean acceptDefaultValue, List<String> excludeFields) {
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        try {
            objectContext.set((BaseRealm) baseRealm, row, columnInfo, acceptDefaultValue, excludeFields);
            checkClass(clazz);

            if (clazz.equals(com.ovchinnikovm.android.vktop.entities.PostSortItem.class)) {
                return clazz.cast(new io.realm.PostSortItemRealmProxy());
            }
            if (clazz.equals(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class)) {
                return clazz.cast(new io.realm.RealmSortedItemRealmProxy());
            }
            if (clazz.equals(com.ovchinnikovm.android.vktop.entities.SocialValue.class)) {
                return clazz.cast(new io.realm.SocialValueRealmProxy());
            }
            throw getMissingProxyClassException(clazz);
        } finally {
            objectContext.clear();
        }
    }

    @Override
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E obj, boolean update, Map<RealmModel, RealmObjectProxy> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.PostSortItem.class)) {
            return clazz.cast(io.realm.PostSortItemRealmProxy.copyOrUpdate(realm, (com.ovchinnikovm.android.vktop.entities.PostSortItem) obj, update, cache));
        }
        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class)) {
            return clazz.cast(io.realm.RealmSortedItemRealmProxy.copyOrUpdate(realm, (com.ovchinnikovm.android.vktop.entities.RealmSortedItem) obj, update, cache));
        }
        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.SocialValue.class)) {
            return clazz.cast(io.realm.SocialValueRealmProxy.copyOrUpdate(realm, (com.ovchinnikovm.android.vktop.entities.SocialValue) obj, update, cache));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public void insert(Realm realm, RealmModel object, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.PostSortItem.class)) {
            io.realm.PostSortItemRealmProxy.insert(realm, (com.ovchinnikovm.android.vktop.entities.PostSortItem) object, cache);
        } else if (clazz.equals(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class)) {
            io.realm.RealmSortedItemRealmProxy.insert(realm, (com.ovchinnikovm.android.vktop.entities.RealmSortedItem) object, cache);
        } else if (clazz.equals(com.ovchinnikovm.android.vktop.entities.SocialValue.class)) {
            io.realm.SocialValueRealmProxy.insert(realm, (com.ovchinnikovm.android.vktop.entities.SocialValue) object, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insert(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.ovchinnikovm.android.vktop.entities.PostSortItem.class)) {
                io.realm.PostSortItemRealmProxy.insert(realm, (com.ovchinnikovm.android.vktop.entities.PostSortItem) object, cache);
            } else if (clazz.equals(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class)) {
                io.realm.RealmSortedItemRealmProxy.insert(realm, (com.ovchinnikovm.android.vktop.entities.RealmSortedItem) object, cache);
            } else if (clazz.equals(com.ovchinnikovm.android.vktop.entities.SocialValue.class)) {
                io.realm.SocialValueRealmProxy.insert(realm, (com.ovchinnikovm.android.vktop.entities.SocialValue) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.ovchinnikovm.android.vktop.entities.PostSortItem.class)) {
                    io.realm.PostSortItemRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class)) {
                    io.realm.RealmSortedItemRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.ovchinnikovm.android.vktop.entities.SocialValue.class)) {
                    io.realm.SocialValueRealmProxy.insert(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, RealmModel obj, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.PostSortItem.class)) {
            io.realm.PostSortItemRealmProxy.insertOrUpdate(realm, (com.ovchinnikovm.android.vktop.entities.PostSortItem) obj, cache);
        } else if (clazz.equals(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class)) {
            io.realm.RealmSortedItemRealmProxy.insertOrUpdate(realm, (com.ovchinnikovm.android.vktop.entities.RealmSortedItem) obj, cache);
        } else if (clazz.equals(com.ovchinnikovm.android.vktop.entities.SocialValue.class)) {
            io.realm.SocialValueRealmProxy.insertOrUpdate(realm, (com.ovchinnikovm.android.vktop.entities.SocialValue) obj, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.ovchinnikovm.android.vktop.entities.PostSortItem.class)) {
                io.realm.PostSortItemRealmProxy.insertOrUpdate(realm, (com.ovchinnikovm.android.vktop.entities.PostSortItem) object, cache);
            } else if (clazz.equals(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class)) {
                io.realm.RealmSortedItemRealmProxy.insertOrUpdate(realm, (com.ovchinnikovm.android.vktop.entities.RealmSortedItem) object, cache);
            } else if (clazz.equals(com.ovchinnikovm.android.vktop.entities.SocialValue.class)) {
                io.realm.SocialValueRealmProxy.insertOrUpdate(realm, (com.ovchinnikovm.android.vktop.entities.SocialValue) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.ovchinnikovm.android.vktop.entities.PostSortItem.class)) {
                    io.realm.PostSortItemRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class)) {
                    io.realm.RealmSortedItemRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.ovchinnikovm.android.vktop.entities.SocialValue.class)) {
                    io.realm.SocialValueRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> clazz, Realm realm, JSONObject json, boolean update)
        throws JSONException {
        checkClass(clazz);

        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.PostSortItem.class)) {
            return clazz.cast(io.realm.PostSortItemRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class)) {
            return clazz.cast(io.realm.RealmSortedItemRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.SocialValue.class)) {
            return clazz.cast(io.realm.SocialValueRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createUsingJsonStream(Class<E> clazz, Realm realm, JsonReader reader)
        throws IOException {
        checkClass(clazz);

        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.PostSortItem.class)) {
            return clazz.cast(io.realm.PostSortItemRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class)) {
            return clazz.cast(io.realm.RealmSortedItemRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.SocialValue.class)) {
            return clazz.cast(io.realm.SocialValueRealmProxy.createUsingJsonStream(realm, reader));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createDetachedCopy(E realmObject, int maxDepth, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) realmObject.getClass().getSuperclass();

        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.PostSortItem.class)) {
            return clazz.cast(io.realm.PostSortItemRealmProxy.createDetachedCopy((com.ovchinnikovm.android.vktop.entities.PostSortItem) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.RealmSortedItem.class)) {
            return clazz.cast(io.realm.RealmSortedItemRealmProxy.createDetachedCopy((com.ovchinnikovm.android.vktop.entities.RealmSortedItem) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.ovchinnikovm.android.vktop.entities.SocialValue.class)) {
            return clazz.cast(io.realm.SocialValueRealmProxy.createDetachedCopy((com.ovchinnikovm.android.vktop.entities.SocialValue) realmObject, 0, maxDepth, cache));
        }
        throw getMissingProxyClassException(clazz);
    }

}
