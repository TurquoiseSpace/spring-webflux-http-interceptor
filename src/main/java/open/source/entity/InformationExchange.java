package open.source.entity;

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
import open.source.enumeration.TimeEvent;
import open.source.model.ExBase;
import open.source.model.ExEnum;
import open.source.model.Information;
import open.source.model.Time;

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
