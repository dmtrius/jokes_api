package pl.jokes.jokesms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Joke {
  @JsonProperty("id")
  private Integer id;
  @JsonProperty("type")
  private String type;
  @JsonProperty("setup")
  private String setup;
  @JsonProperty("punchline")
  private String punchline;
}
