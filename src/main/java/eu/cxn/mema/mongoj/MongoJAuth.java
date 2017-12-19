  /*
   * To change this template, choose Tools | Templates
   * and open the template in the editor.
   */
  package eu.cxn.mema.mongoj;

  import com.fasterxml.jackson.annotation.JsonProperty;
  import com.fasterxml.jackson.annotation.JsonView;
  import eu.cxn.mema.json.Views;

  /**
   * Authorization credentials for MongoJ connector
   *
   * @author kubasek
   */
  public class MongoJAuth {

      /**
       * user name
       */
      @JsonProperty
      @JsonView(Views.Db.class)
      private String user;

      /**
       * password
       */

      @JsonProperty
      @JsonView(Views.Db.class)
      private String passwd;

      public MongoJAuth() {
      }

      public MongoJAuth(String user, String passwd) {
          this.user = user;
          this.passwd = passwd;
      }

      /**
       * @return
       */
      public String getUser() {
          return user;
      }

      /**
       * @return
       */
      public String getPasswd() {
          return passwd;
      }

  }
