package io.onedev.server.search.entity.pullrequest;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import io.onedev.server.model.PullRequest;
import io.onedev.server.model.support.pullrequest.CloseInfo;
import io.onedev.server.search.entity.EntityCriteria;
import io.onedev.server.util.query.PullRequestQueryConstants;

public class StatusCriteria extends EntityCriteria<PullRequest> {

	private static final long serialVersionUID = 1L;

	private String value;
	
	public StatusCriteria(String value) {
		this.value = value;
	}

	@Override
	public Predicate getPredicate(Root<PullRequest> root, CriteriaBuilder builder) {
		Path<?> attribute = PullRequestQuery.getPath(root, PullRequestQueryConstants.ATTR_CLOSE_STATUS);
		if (value.equalsIgnoreCase(PullRequest.STATE_OPEN)) 
			return builder.isNull(attribute);
		else
			return builder.equal(attribute, CloseInfo.Status.valueOf(value.toUpperCase()));
	}

	@Override
	public boolean matches(PullRequest request) {
		if (value.equalsIgnoreCase(PullRequest.STATE_OPEN)) 
			return request.getCloseInfo() == null;
		else
			return request.getCloseInfo() != null && request.getCloseInfo().getStatus().name().equalsIgnoreCase(value);
	}

	@Override
	public String asString() {
		return quote(PullRequestQueryConstants.FIELD_STATUS) + " " 
				+ PullRequestQuery.getRuleName(PullRequestQueryLexer.Is) + " " 
				+ quote(value);
	}

}
