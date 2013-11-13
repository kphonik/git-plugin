package hudson.plugins.git.util;

import hudson.Extension;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.plugins.git.GitSCM;
import hudson.plugins.git.Messages;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

/**
 * A {@link ParameterDefinition} that uses a set name of {@link GitSCM#GIT_BRANCH} and
 * accepts a single string value to create a {@link BranchParameterValue}. Use of this
 * definition is not required - any {@link hudson.model.StringParameterValue} with a
 * name matching {@link GitSCM#GIT_BRANCH} will suffice.
 *
 * @author jason@stiefel.io
 */
public class BranchParameterDefinition extends ParameterDefinition {

    @DataBoundConstructor
    public BranchParameterDefinition() {
        super(GitSCM.GIT_BRANCH, Messages.BuildChooser_BranchParameter_Description());
    }

    public String getTitle() {
        return Messages.BuildChooser_BranchParameter();
    }

    @Override
    public ParameterValue createValue(StaplerRequest staplerRequest, JSONObject jsonObject) {
        return new BranchParameterValue(getName(), jsonObject.getString("value"));
    }

    @Override
    public ParameterValue createValue(StaplerRequest staplerRequest) {
        return new BranchParameterValue(getName(), staplerRequest.getParameter(getName()));
    }

    @Extension
    public static class DescriptorImpl extends ParameterDescriptor {
        @Override
        public String getDisplayName() {
            return Messages.BuildChooser_BranchParameter();
        }
    }

}
