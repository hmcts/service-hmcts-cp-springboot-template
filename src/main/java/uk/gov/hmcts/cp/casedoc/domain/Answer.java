package uk.gov.hmcts.cp.casedoc.domain;

import java.util.List;

public record Answer(String content, List<String> citations) {

}
