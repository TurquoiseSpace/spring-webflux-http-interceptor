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
public class ExZonedDateTime extends ExBase implements Serializable {

	private ExChronology chronology;

	private int dayOfMonth;

	// TODO : parse and build object
	private Object dayOfWeek;

	private int dayOfYear;

	private int hour;

	private int minute;

	// TODO : parse and build object
	private Object month;

	private int monthValue;

	private int nano;

	// TODO : parse and build object
	private Object offset;

	private int second;

	private int year;

	// TODO : parse and build object
	private Object zone;

	private long toEpochSecond;

	private ExInstant toInstant;

	// TODO : parse and build object
	private Object toLocalDate;

	// TODO : parse and build object
	private Object toLocalDateTime;

	// TODO : parse and build object
	private Object toLocalTime;

	// TODO : parse and build object
	private Object toOffsetDateTime;

	private ExZonedDateTime withEarlierOffsetAtOverlap;

	private ExZonedDateTime withFixedOffsetZone;

	private ExZonedDateTime withLaterOffsetAtOverlap;

	public ExZonedDateTime(ExBase exBase) {

		if (null != exBase) {
			this.setClazz(exBase.getClazz());
			this.setHashCode(exBase.getHashCode());
			this.setToString(exBase.getToString());
		}
	}

}
