package open.source.exchange.entity;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import open.source.exchange.enumeration.TimeEvent;
import open.source.exchange.model.ExBase;
import open.source.exchange.model.ExEnum;
import open.source.exchange.model.Information;
import open.source.exchange.model.Time;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(value = "informationexchange")
public class InformationExchange extends Information implements Serializable {

	@Id
	// requestId + startTimestamp
	private String id;

	private ExEnum signalType;

	@Indexed(background = true)
	private Map<TimeEvent, Time<Long>> events;

	public InformationExchange(ExBase exBase) {

		super(exBase);
	}

}
