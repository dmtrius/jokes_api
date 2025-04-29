package pl.jokes.jokesms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
public class Joke {
  public Joke() {
  }

  public Joke(Integer id, String type, String setup, String punchline) {
    this.id = id;
    this.type = type;
    this.setup = setup;
    this.punchline = punchline;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSetup() {
    return setup;
  }

  public void setSetup(String setup) {
    this.setup = setup;
  }

  public String getPunchline() {
    return punchline;
  }

  public void setPunchline(String punchline) {
    this.punchline = punchline;
  }

  @JsonProperty("id")
  private Integer id;
  @JsonProperty("type")
  private String type;
  @JsonProperty("setup")
  private String setup;
  @JsonProperty("punchline")
  private String punchline;
}
