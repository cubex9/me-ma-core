package eu.cxn.mema.skeleton;

public interface IScript extends IEntity {

    /**
     * name of script, it is nesessary
     *
     * @return
     */
    String name();

    /**
     * place where is script, if == null, must have body()
     */

    String path();


    String body();

    /* runnable parameters */
    String params();
}
