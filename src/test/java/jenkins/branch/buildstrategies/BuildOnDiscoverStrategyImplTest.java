package jenkins.branch.buildstrategies;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import jenkins.branch.buildstrategies.BuildOnDiscoverStrategyImpl;
import jenkins.scm.api.SCMHeadOrigin;
import jenkins.scm.api.mixin.ChangeRequestCheckoutStrategy;
import jenkins.scm.impl.mock.MockChangeRequestSCMHead;
import jenkins.scm.impl.mock.MockChangeRequestSCMRevision;
import jenkins.scm.impl.mock.MockSCMController;
import jenkins.scm.impl.mock.MockSCMHead;
import jenkins.scm.impl.mock.MockSCMRevision;
import jenkins.scm.impl.mock.MockSCMSource;
import jenkins.scm.impl.mock.MockTagSCMHead;

/**
 * Test the ${@link BuildOnDiscoverStrategyImpl} class.
 */
public class BuildOnDiscoverStrategyImplTest {
    @Test
    public void given__regular_head_discovery__when__isAutomaticBuild__then__returns_true() throws Exception {
        try (MockSCMController c = MockSCMController.create()) {
            MockSCMHead head = new MockSCMHead("master");
            assertThat(
                    new BuildOnDiscoverStrategyImpl().isAutomaticBuild(
                            new MockSCMSource(c, "dummy"),
                            head,
                            new MockSCMRevision(head, "dummy"),
                            null
                    ),
                    is(true)
            );
        }
    }

    @Test
    public void given__regular_head_change__when__isAutomaticBuild__then__returns_false() throws Exception {
        try (MockSCMController c = MockSCMController.create()) {
            MockSCMHead head = new MockSCMHead("master");
            assertThat(
                    new BuildOnDiscoverStrategyImpl().isAutomaticBuild(
                            new MockSCMSource(c, "dummy"),
                            head,
                            new MockSCMRevision(head, "dummy"),
                            new MockSCMRevision(head, "previous dummy")
                    ),
                    is(false)
            );
        }
    }

    @Test
    public void given__tag_head_discovery__when__isAutomaticBuild__then__returns_false() throws Exception {
        try (MockSCMController c = MockSCMController.create()) {
            MockSCMHead head = new MockTagSCMHead("master", System.currentTimeMillis());
            assertThat(
                    new BuildOnDiscoverStrategyImpl().isAutomaticBuild(
                            new MockSCMSource(c, "dummy"),
                            head,
                            new MockSCMRevision(head, "dummy"),
                            null
                    ),
                    is(false)
            );
        }
    }

    @Test
    public void given__tag_head_change__when__isAutomaticBuild__then__returns_false() throws Exception {
        try (MockSCMController c = MockSCMController.create()) {
            MockSCMHead head = new MockTagSCMHead("master", System.currentTimeMillis());
            assertThat(
                    new BuildOnDiscoverStrategyImpl().isAutomaticBuild(
                            new MockSCMSource(c, "dummy"),
                            head,
                            new MockSCMRevision(head, "dummy"),
                            new MockSCMRevision(head, "previous dummy")
                    ),
                    is(false)
            );
        }
    }

    @Test
    public void given__cr_head_discovery__when__isAutomaticBuild__then__returns_true() throws Exception {
        try (MockSCMController c = MockSCMController.create()) {
            MockChangeRequestSCMHead head = new MockChangeRequestSCMHead(SCMHeadOrigin.DEFAULT, 1, "master",
                    ChangeRequestCheckoutStrategy.MERGE, true);
            assertThat(
                    new BuildOnDiscoverStrategyImpl().isAutomaticBuild(
                            new MockSCMSource(c, "dummy"),
                            head,
                            new MockChangeRequestSCMRevision(head,
                                    new MockSCMRevision(new MockSCMHead("master"), "dummy"), "dummy"),
                            null
                    ),
                    is(true)
            );
        }
    }

    @Test
    public void given__cr_head_change__when__isAutomaticBuild__then__returns_false() throws Exception {
        try (MockSCMController c = MockSCMController.create()) {
            MockChangeRequestSCMHead head = new MockChangeRequestSCMHead(SCMHeadOrigin.DEFAULT, 1, "master",
                    ChangeRequestCheckoutStrategy.MERGE, true);
            assertThat(
                    new BuildOnDiscoverStrategyImpl().isAutomaticBuild(
                            new MockSCMSource(c, "dummy"),
                            head,
                            new MockChangeRequestSCMRevision(head,
                                    new MockSCMRevision(new MockSCMHead("master"), "dummy"), "dummy"),
                            new MockSCMRevision(head, "previous dummy")
                    ),
                    is(false)
            );
        }
    }
}