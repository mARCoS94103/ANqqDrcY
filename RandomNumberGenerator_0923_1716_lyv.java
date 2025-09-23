// 代码生成时间: 2025-09-23 17:16:09
import java.util.Random;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

/**
 * RandomNumberGenerator Controller class
 * This class handles requests to generate random numbers.
 */
@With(SecurityAction.class) // Assuming SecurityAction is a custom security action
public class RandomNumberGenerator extends Controller {

    private final Random random = new Random();

    /**
     * Generates a random integer between a specified range.
     *
     * @param min Minimum value of the range (inclusive)
     * @param max Maximum value of the range (inclusive)
     * @return A Result containing the generated random number as JSON
     */
    public Result generateRandomNumber(int min, int max) {
        if (min >= max) {
            // Return a bad request if the range is invalid
            return badRequest("You must specify a valid range where min < max");
        }

        // Generate a random integer within the specified range
        int randomNumber = random.nextInt(max - min + 1) + min;

        // Return the generated random number as JSON
        return ok(Json.toJson(randomNumber));
    }

    /**
     * Default endpoint for generating a random number between 0 and 100.
     *
     * @return A Result containing the generated random number as JSON
     */
    public Result generateRandomNumber() {
        return generateRandomNumber(0, 100);
    }

    /**
     * Security action to restrict access to the random number generator.
     * This is a placeholder method and should be replaced with actual security logic.
     */
    public static class SecurityAction extends Action.Simple {
        @Override
        public SimpleResult execute(Http.Context ctx) {
            // Add your security logic here
            // For simplicity, this example allows all requests to pass through
            return delegate.call(ctx);
        }
    }
}
