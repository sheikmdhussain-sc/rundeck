package rundeck.data.job

import com.dtolabs.rundeck.core.jobs.JobOption
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import grails.validation.Validateable
import org.rundeck.app.data.model.v1.job.option.OptionData
import org.rundeck.app.data.model.v1.job.option.OptionValueData
import rundeck.data.validation.shared.SharedJobOptionConstraints

@JsonIgnoreProperties(["errors"])
class RdOption implements JobOption, OptionData, Comparable<OptionData>, Validateable {
    Long id;
    String name;
    Integer sortIndex;
    String description;
    String defaultValue;
    String defaultStoragePath;
    Boolean enforced;
    Boolean required;
    Boolean isDate;
    String dateFormat;
    String label;
    URL realValuesUrl;
    String regex;
    String valuesList;
    String valuesListDelimiter;
    Boolean multivalued;
    String delimiter;
    Boolean secureInput;
    Boolean secureExposed;
    String optionType;
    Map<String,Object> configMap;
    Boolean multivalueAllSelected;
    String optionValuesPluginType;
    List<RdOptionValue> valuesFromPlugin;
    Boolean hidden;
    Boolean sortValues;
    List<String> optionValues;

    static constraints={
        importFrom(SharedJobOptionConstraints)
        id(nullable: true)
        realValuesUrl(nullable: true)
    }

    static RdOption convertFromJobOption(JobOption jobOption, RdOption orig) {
        RdOption o = orig ?: new RdOption()
        o.sortIndex = jobOption.sortIndex
        o.name = jobOption.name
        o.description = jobOption.description
        o.defaultValue = jobOption.defaultValue
        o.defaultStoragePath = jobOption.defaultStoragePath
        o.enforced = jobOption.enforced
        o.required = jobOption.required
        o.isDate = jobOption.getIsDate()
        o.dateFormat = jobOption.dateFormat
        o.realValuesUrl = jobOption.realValuesUrl
        o.label = jobOption.label
        o.regex = jobOption.regex
        o.valuesList = jobOption.valuesList
        o.valuesListDelimiter = jobOption.valuesListDelimiter
        o.delimiter = jobOption.delimiter
        o.multivalued = jobOption.multivalued
        o.secureInput = jobOption.secureInput
        o.secureExposed = jobOption.secureExposed
        o.optionType = jobOption.optionType
        o.configMap = jobOption.configData ? mapper.readValue(jobOption.configData, HashMap<String,Object>) : null
        o.multivalueAllSelected = jobOption.multivalueAllSelected
        o.optionValuesPluginType = jobOption.optionValuesPluginType
        o.valuesFromPlugin = orig?.valuesFromPlugin
        o.hidden = jobOption.hidden
        o.sortValues = jobOption.sortValues
        o.optionValues = jobOption.optionValues ? new ArrayList(jobOption.optionValues) : null
        return o
    }

    @Override
    public int compareTo(OptionData obj) {
        if (null != sortIndex && null != obj.sortIndex) {
            return sortIndex.compareTo(obj.sortIndex);
        } else if (null == sortIndex && null == obj.sortIndex && name) {
            return name.compareTo(obj.name);
        } else {
            return sortIndex != null ? -1 : 1;
        }
    }

    static class RdOptionValue implements OptionValueData {
        String name;
        String value;
    }

    static ObjectMapper mapper = new ObjectMapper()
}
