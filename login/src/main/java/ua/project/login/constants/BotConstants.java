package ua.project.login.constants;

public class BotConstants {

    public static final String START_GREETING_TEXT = """
            Hi, <b>%s</b>!
            Send me your login code to continue.
            """;

    public static final String ERROR_CODE_NOT_EXISTS_TEXT = "Code not found.";

    public static final String ERROR_CODE_WAS_EXPIRED = "Code was expired.\nRefresh page and try again.";

    public static final String ERROR_MESSAGE_LENGTH_TEXT = "Send a valid login code.";

    public static final String SEND_PHOTO_TEXT = "Code found.\nNow send picture for your game profile.";
    public static final String SEND_PHOTO_ERROR_TEXT = "Invalid photo.";

    public static final String SUCCESS_AUTH_TEXT = """
            Successful login.
            Page will redirect automatically.
            
            <b>Don't delete or stop bot that will be cause lost all of your game data.</b>
            """;

}