package cz.zcu.kiv.pia.martinm.internetbanking.service;

/**
 * MessageContainer for messages targeted to view.
 *
 * Date: 25.12.2018
 *
 * @author Martin Matas
 */
public class MessageContainer {

    /**
     * Defines message type, for more details se {@link Type}.
     */
    private Type type;

    /**
     * Content of this message.
     */
    private String content;

    /**
     * Creates new message.
     *
     * @param type - message type
     * @param content - message content
     */
    public MessageContainer(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    /**
     * Enumeration of message types that can be used according to Bootstrap 4.
     * Each types is represented by another color see Bootstrap 4 Documentation for more details.
     */
    public enum Type {

         PRIMARY    ("primary"),
         SUCCESS    ("success"),
         DANGER     ("danger"),
         WARNING    ("warning"),
         INFO       ("info"),
         DARK       ("dark");

        /**
         * CSS style suffix
         */
        private final String cssAlertSuffix;

        Type (String cssAlertSuffix) {
             this.cssAlertSuffix = cssAlertSuffix;
        }

        public String getValue() {
            return cssAlertSuffix;
        }

    }

}
