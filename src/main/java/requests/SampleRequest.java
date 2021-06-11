package requests;

/**
 * Example Request for testing.
 */
public class SampleRequest {

  public final int id;
  public final String name;

  /**
   * Create a Request with given params.
   *
   * @param id some int
   * @param name some string
   */
  public SampleRequest(int id, String name) {
    this.id = id;
    this.name = name;
  }
}
