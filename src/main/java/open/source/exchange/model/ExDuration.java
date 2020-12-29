package open.source.exchange.model;

import java.io.Serializable;
import java.util.List;

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
public class ExDuration extends ExBase implements Serializable {

	private long seconds;

	private int nano;

	private List<ExTemporalUnit> units;

	private boolean isNegative;

	private boolean isZero;

	private long toDays;

	private long toHours;

	private long toMinutes;

	private long toMillis;

	private long toNanos;

	private ExDuration absolute;

	private ExDuration negated;

	public ExDuration(ExBase exBase) {

		if (null != exBase) {
			this.setClazz(exBase.getClazz());
			this.setHashCode(exBase.getHashCode());
			this.setToString(exBase.getToString());
		}
	}

}
