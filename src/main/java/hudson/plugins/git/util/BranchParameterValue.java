package hudson.plugins.git.util;

import hudson.model.StringParameterValue;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Nothing special about this parameter value. The {@link BranchBuildChooser} will use any
 * {@link StringParameterValue} instance with a name matching
 * {@link hudson.plugins.git.GitSCM#GIT_BRANCH}.
 *
 * @author jason@stiefel.io
 */
public class BranchParameterValue extends StringParameterValue {

    @DataBoundConstructor
    public BranchParameterValue(String name, String value) {
        super(name, value);
    }

}
