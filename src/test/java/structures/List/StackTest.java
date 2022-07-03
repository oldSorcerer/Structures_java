package structures.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    private Stack<String> stringStack;

    @BeforeEach
    public void init() {
        stringStack = new Stack<>();
    }

    public void fillStack() {
        stringStack.push("Привет");
        stringStack.push("меня");
        stringStack.push("зовут");
        stringStack.push("Собака");
    }

    @Test
    void push() {
        fillStack();
        assertEquals(4, stringStack.getSizeStack());
        assertEquals("Собака", stringStack.pop());
        assertEquals("зовут", stringStack.pop());
        assertEquals(2, stringStack.getSizeStack());
    }

    @Test
    void pop() {
        assertNull(stringStack.pop());
        assertEquals(0, stringStack.getSizeStack());
    }

    @Test
    void peek() {
        fillStack();
        assertEquals(4, stringStack.getSizeStack());
        assertEquals("Собака", stringStack.peek());
        assertEquals("Собака", stringStack.peek());
        assertEquals(4, stringStack.getSizeStack());
    }

    @Test
    void pushNullException(){
        assertThrows(IllegalArgumentException.class, () -> stringStack.push(null));
    }

    @Test
    void pushNullMessage() {
        try {
            stringStack.push(null);
            fail("No exception");
        }
        catch (IllegalArgumentException e){
            assertEquals("New element cannot be null", e.getMessage());
       }
    }
}