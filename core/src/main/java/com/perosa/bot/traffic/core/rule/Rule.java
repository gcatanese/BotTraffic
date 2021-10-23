package com.perosa.bot.traffic.core.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perosa.bot.traffic.core.service.ConsumableService;
import com.perosa.bot.traffic.core.service.TargetUrl;

import java.util.List;
import java.util.UUID;

public class Rule {

    private String id;
    private String path;
    private String expression;
    private Operator operator;
    private String value;
    private List<ConsumableService> targetServices;
    private List<TargetUrl> targetUrls;
    private RuleType type;
    private RuleStatus status = RuleStatus.ACTIVE;
    private RuleWorkflow workflow = RuleWorkflow.ROUTE;
    private boolean catchAll = false;

    public Rule() {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }

    public Rule(String id) {
        this.id = id;
    }

    public Rule(String id, String path, boolean catchAll, List<ConsumableService> targetServices) {
        this(id);
        this.path = path;
        this.catchAll = catchAll;
        this.targetServices = targetServices;
    }

    public Rule(String path, String expression, String value, Operator operator, RuleType type, List<ConsumableService> targetServices) {
        this();

        this.path = path;
        this.expression = expression;
        this.operator = operator;
        this.value = value;
        this.type = type;
        this.targetServices = targetServices;
    }

    public Rule(String id, String path, String expression, String value, Operator operator, RuleType type, List<ConsumableService> targetServices) {
        this.id = id;
        this.path = path;
        this.expression = expression;
        this.operator = operator;
        this.value = value;
        this.type = type;
        this.targetServices = targetServices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<ConsumableService> getTargetServices() {
        return targetServices;
    }

    public void setTargetServices(List<ConsumableService> targetServices) {
        this.targetServices = targetServices;
    }

    public RuleType getType() {
        return type;
    }

    public void setType(RuleType type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RuleStatus getStatus() {
        return status;
    }

    public void setStatus(RuleStatus status) {
        this.status = status;
    }

    public RuleWorkflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(RuleWorkflow workflow) {
        this.workflow = workflow;
    }

    public boolean isCatchAll() {
        return catchAll;
    }

    public void setCatchAll(boolean catchAll) {
        this.catchAll = catchAll;
    }

    public List<TargetUrl> getTargetUrls() {
        return targetUrls;
    }

    public void setTargetUrls(List<TargetUrl> targetUrls) {
        this.targetUrls = targetUrls;
    }

    public String asJson() {

        String ret = "";

        ObjectMapper mapper = new ObjectMapper();

        try {
            ret = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ret;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if(obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Rule rule = (Rule)obj;

        return rule.getId().equals(this.id);
    }

    @Override
    public String toString() {
        return "Rule[" +
                "id:" + id +
                ", path:" + path +
                ", type:" + type +
                ", operator:" + operator +
                ", expression:" + expression +
                ", value:" + value +
                ", targetServices:" + targetServices +
                ", targetUrls:" + targetUrls +
                "]";
    }

}
