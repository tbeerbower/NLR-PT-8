package org.tbeerbower;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.tbeerbower.MainMenu;
import org.tbeerbower.view.View;

public class MainMenuTest {
    private View mockView;
    private MainMenu menu;

    @Before
    public void setUp() {
        mockView = mock(View.class);
        menu = new MainMenu(mockView);
    }

    @Test
    public void showOptions() {
        when(mockView.getUserInt()).thenReturn(4);

        MainMenu.Option selectedOption = menu.showOptions();

        InOrder inOrder = inOrder(mockView);
        inOrder.verify(mockView).displayDivider();
        inOrder.verify(mockView).displayLine("1) Play Wordle game");
        inOrder.verify(mockView).displayLine("2) Play Wordle Peaks game");
        inOrder.verify(mockView).displayLine("3) Change player");
        inOrder.verify(mockView).displayLine("4) Display player statistics");
        inOrder.verify(mockView).displayLine("5) Display leaderboards");
        inOrder.verify(mockView).displayLine("6) Exit");
        inOrder.verify(mockView).displayLine("Please enter your choice (1-6)");
        inOrder.verify(mockView).getUserInt();

        assertEquals(MainMenu.Option.DISPLAY_PLAYER_STATS, selectedOption);
    }
}
