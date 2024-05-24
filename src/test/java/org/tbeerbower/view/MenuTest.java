package org.tbeerbower.view;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class MenuTest {
    private View mockView;
    private Menu<String> menu;

    @Before
    public void setUp() {
        mockView = mock(View.class);
        String[] options = { "Option 1", "Option 2", "Option 3" };
        menu = new Menu<>(options, mockView);
    }

    @Test
    public void show() {
        when(mockView.getUserInt()).thenReturn(2);

        int choice = menu.show();

        InOrder inOrder = inOrder(mockView);
        inOrder.verify(mockView).displayDivider();
        inOrder.verify(mockView).displayLine("1) Option 1");
        inOrder.verify(mockView).displayLine("2) Option 2");
        inOrder.verify(mockView).displayLine("3) Option 3");
        inOrder.verify(mockView).displayLine("Please enter your choice (1-3)");
        inOrder.verify(mockView).getUserInt();

        assertEquals(2, choice);
    }

    @Test
    public void show_invalidChoice() {
        when(mockView.getUserInt()).thenReturn(4, 1);

        int choice = menu.show();

        InOrder inOrder = inOrder(mockView);
        inOrder.verify(mockView).displayDivider();
        inOrder.verify(mockView).displayLine("1) Option 1");
        inOrder.verify(mockView).displayLine("2) Option 2");
        inOrder.verify(mockView).displayLine("3) Option 3");
        inOrder.verify(mockView).displayLine("Please enter your choice (1-3)");
        inOrder.verify(mockView).getUserInt();
        inOrder.verify(mockView).displayLine("4 is not a valid choice!");
        inOrder.verify(mockView).displayLine("Please enter your choice (1-3)");
        inOrder.verify(mockView).getUserInt();

        assertEquals(1, choice);
    }

    @Test
    public void showOptions() {
        when(mockView.getUserInt()).thenReturn(3);

        String selectedOption = menu.showOptions();

        InOrder inOrder = inOrder(mockView);
        inOrder.verify(mockView).displayDivider();
        inOrder.verify(mockView).displayLine("1) Option 1");
        inOrder.verify(mockView).displayLine("2) Option 2");
        inOrder.verify(mockView).displayLine("3) Option 3");
        inOrder.verify(mockView).displayLine("Please enter your choice (1-3)");
        inOrder.verify(mockView).getUserInt();

        assertEquals("Option 3", selectedOption);
    }
}
