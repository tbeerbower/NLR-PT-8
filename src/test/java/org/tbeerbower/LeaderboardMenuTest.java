package org.tbeerbower;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.tbeerbower.view.View;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LeaderboardMenuTest {
    private View mockView;
    private LeaderboardMenu menu;

    @Before
    public void setUp() {
        mockView = mock(View.class);
        menu = new LeaderboardMenu(mockView);
    }

    @Test
    public void showOptions() {
        when(mockView.getUserInt()).thenReturn(2);

        LeaderboardMenu.Option selectedOption = menu.showOptions();

        InOrder inOrder = inOrder(mockView);
        inOrder.verify(mockView).displayDivider();
        inOrder.verify(mockView).displayLine("1) Leaderboard by games won");
        inOrder.verify(mockView).displayLine("2) Leaderboard by average guesses");
        inOrder.verify(mockView).displayLine("3) Return to main menu");
        inOrder.verify(mockView).displayLine("Please enter your choice (1-3)");
        inOrder.verify(mockView).getUserInt();

        assertEquals(LeaderboardMenu.Option.BY_AVG_GUESSES, selectedOption);
    }
}
