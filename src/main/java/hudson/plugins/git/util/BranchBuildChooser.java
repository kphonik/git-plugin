package hudson.plugins.git.util;

import hudson.Extension;
import hudson.model.ParameterValue;
import hudson.model.ParametersAction;
import hudson.model.StringParameterValue;
import hudson.model.TaskListener;
import hudson.plugins.git.GitException;
import hudson.plugins.git.GitSCM;
import hudson.plugins.git.Messages;
import hudson.plugins.git.Revision;
import org.jenkinsci.plugins.gitclient.GitClient;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.util.Collection;

/**
 * A {@link BuildChooser} "decorator" of sorts that looks at the build for an incoming parameter
 * with a name matching {@link GitSCM#GIT_BRANCH}. If we find one then we'll tell our delegate
 * {@link BuildChooser} to use that {@code singleBranch} as its source.
 *
 * @author jason@stiefel.io
 */
public class BranchBuildChooser extends DefaultBuildChooser {

    @DataBoundConstructor
    public BranchBuildChooser() {
    }

    @Override
    public Collection<Revision> getCandidateRevisions(boolean isPollCall, String singleBranch,
                                                      GitClient git, TaskListener listener,
                                                      BuildData data, BuildChooserContext context)
            throws GitException, IOException, InterruptedException {

        String parameterBranch = null;
        ParametersAction parametersAction = context.getBuild().getAction(ParametersAction.class);
        if (parametersAction != null) {
            ParameterValue parameter = parametersAction.getParameter(GitSCM.GIT_BRANCH);
            if (parameter instanceof StringParameterValue) {
                parameterBranch = ((StringParameterValue)parameter).value;
            } else {
                listener.getLogger().println(Messages._BuildChooser_BranchParameter_InvalidParameter(parameter.getClass().getSimpleName()));
            }
        }

        if (parameterBranch != null) {
            singleBranch = parameterBranch;
            listener.getLogger().println(Messages._BuildChooser_BranchParameter_WillBuild(singleBranch));
        } else {
            listener.getLogger().println(Messages._BuildChooser_BranchParameter_NoParameter());
        }

        return super.getCandidateRevisions(isPollCall, singleBranch, git, listener, data, context);

    }

    @Extension
    public static final class DescriptorImpl extends BuildChooserDescriptor {
        @Override
        public String getDisplayName() {
            return Messages.BuildChooser_BranchParameter();
        }
    }
}
