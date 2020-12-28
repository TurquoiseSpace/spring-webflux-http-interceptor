package open.source.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExDate extends ExBase implements Serializable {

	// milliseconds since January 1, 1970, 00:00:00 GMT
	private long timestamp;

	private String toGMTString;

	// measured in minutes, with respect to GMT
	private int timezoneOffset;

	private String toLocaleString;

	// actual year minus 1900
	private int year;

	// 0 is January, 1 is February .. 11 is December
	private int month;

	// 1 till 31
	private int day;

	// 0 is Sunday, 1 is Monday .. 6 is Saturday
	private int weekday;

	// 0 till 23
	private int hour;

	// 0 till 59
	private int minute;

	// 0 till 61
	private int second;

	private ExInstant instant;

	public ExDate(ExBase exBase) {

		if (null != exBase) {
			this.setClazz(exBase.getClazz());
			this.setHashCode(exBase.getHashCode());
			this.setToString(exBase.getToString());
		}
	}

}
