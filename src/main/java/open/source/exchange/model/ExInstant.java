package open.source.exchange.model;

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
public class ExInstant extends ExBase implements Serializable {

	// the number of seconds from the epoch of 1970-01-01T00:00:00Z
	private long epochSecond;

	// number of nanoseconds, later along the time-line, from the start of the second
	private int nano;

	// the number of milliseconds since the epoch of 1970-01-01T00:00:00Z
	private long toEpochMilli;

	public ExInstant(ExBase exBase) {

		if (null != exBase) {
			this.setClazz(exBase.getClazz());
			this.setHashCode(exBase.getHashCode());
			this.setToString(exBase.getToString());
		}
	}

}
