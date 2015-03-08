package goldengoblet;

import goldengoblet.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "verbendpoint", namespace = @ApiNamespace(ownerDomain = "mycompany.com", ownerName = "mycompany.com", packagePath="services"))
public class VerbEndpoint {

  /**
   * This method lists all the entities inserted in datastore.
   * It uses HTTP GET method and paging support.
   *
   * @return A CollectionResponse class containing the list of all entities
   * persisted and a cursor to the next page.
   */
  @SuppressWarnings({"unchecked", "unused"})
  @ApiMethod(name = "listVerb")
  public CollectionResponse<Verb> listVerb(
    @Nullable @Named("cursor") String cursorString,
    @Nullable @Named("limit") Integer limit) {

    PersistenceManager mgr = null;
    Cursor cursor = null;
    List<Verb> execute = null;

    try{
      mgr = getPersistenceManager();
      Query query = mgr.newQuery(Verb.class);
      if (cursorString != null && cursorString != "") {
        cursor = Cursor.fromWebSafeString(cursorString);
        HashMap<String, Object> extensionMap = new HashMap<String, Object>();
        extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
        query.setExtensions(extensionMap);
      }

      if (limit != null) {
        query.setRange(0, limit);
      }

      execute = (List<Verb>) query.execute();
      cursor = JDOCursorHelper.getCursor(execute);
      if (cursor != null) cursorString = cursor.toWebSafeString();

      // Tight loop for fetching all entities from datastore and accomodate
      // for lazy fetch.
      for (Verb obj : execute);
    } finally {
      mgr.close();
    }

    return CollectionResponse.<Verb>builder()
      .setItems(execute)
      .setNextPageToken(cursorString)
      .build();
  }

  /**
   * This method gets the entity having primary key id. It uses HTTP GET method.
   *
   * @param id the primary key of the java bean.
   * @return The entity with primary key id.
   */
  @ApiMethod(name = "getVerb")
  public Verb getVerb(@Named("id") Long id) {
    PersistenceManager mgr = getPersistenceManager();
    Verb verb  = null;
    try {
      verb = mgr.getObjectById(Verb.class, id);
    } finally {
      mgr.close();
    }
    return verb;
  }

  /**
   * This inserts a new entity into App Engine datastore. If the entity already
   * exists in the datastore, an exception is thrown.
   * It uses HTTP POST method.
   *
   * @param verb the entity to be inserted.
   * @return The inserted entity.
   */
  @ApiMethod(name = "insertVerb")
  public Verb insertVerb(Verb verb) {
    PersistenceManager mgr = getPersistenceManager();
    try {
    	if(verb.getId()!=null)
      if(containsVerb(verb)) {
        throw new EntityExistsException("Object already exists");
      }
      mgr.makePersistent(verb);
    } finally {
      mgr.close();
    }
    return verb;
  }

  /**
   * This method is used for updating an existing entity. If the entity does not
   * exist in the datastore, an exception is thrown.
   * It uses HTTP PUT method.
   *
   * @param verb the entity to be updated.
   * @return The updated entity.
   */
  @ApiMethod(name = "updateVerb")
  public Verb updateVerb(Verb verb) {
    PersistenceManager mgr = getPersistenceManager();
    try {
      if(!containsVerb(verb)) {
        throw new EntityNotFoundException("Object does not exist");
      }
      mgr.makePersistent(verb);
    } finally {
      mgr.close();
    }
    return verb;
  }

  /**
   * This method removes the entity with primary key id.
   * It uses HTTP DELETE method.
   *
   * @param id the primary key of the entity to be deleted.
   */
  @ApiMethod(name = "removeVerb")
  public void removeVerb(@Named("id") Long id) {
    PersistenceManager mgr = getPersistenceManager();
    try {
      Verb verb = mgr.getObjectById(Verb.class, id);
      mgr.deletePersistent(verb);
    } finally {
      mgr.close();
    }
  }

  private boolean containsVerb(Verb verb) {
    PersistenceManager mgr = getPersistenceManager();
    boolean contains = true;
    try {
      mgr.getObjectById(Verb.class, verb.getId());
    } catch (javax.jdo.JDOObjectNotFoundException ex) {
      contains = false;
    } finally {
      mgr.close();
    }
    return contains;
  }

  private static PersistenceManager getPersistenceManager() {
    return PMF.get().getPersistenceManager();
  }

}
