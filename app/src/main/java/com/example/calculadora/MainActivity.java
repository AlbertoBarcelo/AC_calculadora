package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView resultText;
    private double firstNumber = 0;
    private double secondNumber = 0;
    private String operator = "";
    private boolean isOperatorSelected = false;
    private double memoryValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = findViewById(R.id.result_text);

        setupNumberButtonListeners();
        setupOperatorButtonListeners();
        setupClearButtonListener();
        setupBackspaceButtonListener();
        setupMemoryButtons();
    }

    private void setupNumberButtonListeners() {
        int[] numberButtonIds = {
                R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
                R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7,
                R.id.button_8, R.id.button_9
        };

        for (int id : numberButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number = ((Button) view).getText().toString();
                    if (isOperatorSelected) {
                        resultText.setText(number);
                        isOperatorSelected = false;
                    } else {
                        resultText.setText(resultText.getText().equals("0") ? number : resultText.getText() + number);
                    }
                }
            });
        }
    }

    private void setupOperatorButtonListeners() {
        int[] operatorButtonIds = {
                R.id.button_add, R.id.button_subtract, R.id.button_multiply, R.id.button_divide
        };

        for (int id : operatorButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    operator = ((Button) view).getText().toString();
                    firstNumber = Double.parseDouble(resultText.getText().toString());
                    isOperatorSelected = true;
                }
            });
        }

        Button equalsButton = findViewById(R.id.button_equals);
        equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondNumber = Double.parseDouble(resultText.getText().toString());
                double result = performOperation(firstNumber, secondNumber, operator);
                resultText.setText(String.valueOf(result));
                operator = "";
                isOperatorSelected = true;
            }
        });
    }

    private void setupClearButtonListener() {
        Button clearButton = findViewById(R.id.button_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText.setText("0");
                firstNumber = 0;
                secondNumber = 0;
                operator = "";
                isOperatorSelected = false;
            }
        });
    }

    private void setupBackspaceButtonListener() {
        Button backspaceButton = findViewById(R.id.button_backspace);
        backspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText = resultText.getText().toString();
                if (currentText.length() > 1) {
                    resultText.setText(currentText.substring(0, currentText.length() - 1));
                } else {
                    resultText.setText("0");
                }
            }
        });
    }

    private void setupMemoryButtons() {
        Button memoryStoreButton = findViewById(R.id.button_memory_store);
        Button memoryRecallButton = findViewById(R.id.button_memory_recall);

        memoryStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memoryValue = Double.parseDouble(resultText.getText().toString());
            }
        });

        memoryRecallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText.setText(String.valueOf(memoryValue));
            }
        });
    }

    private double performOperation(double first, double second, String operator) {
        switch (operator) {
            case "+":
                return first + second;
            case "-":
                return first - second;
            case "*":
                return first * second;
            case "/":
                if (second != 0) return first / second;
                else {
                    resultText.setText("Error");
                    return 0;
                }
            default:
                return second;
        }
    }
}
