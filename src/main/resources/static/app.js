document.addEventListener('DOMContentLoaded', function() {
    const promptInput = document.getElementById('prompt-input');
    const submitBtn = document.getElementById('submit-btn');
    const clearBtn = document.getElementById('clear-btn');
    const responseContainer = document.getElementById('response-container');

    let eventSource = null;

    // Функция для отправки промпта и обработки SSE
    async function sendPrompt() {
        const prompt = promptInput.value.trim();

        if (!prompt) {
            alert('Пожалуйста, введите промпт');
            return;
        }

        submitBtn.disabled = true;
        responseContainer.textContent = '';

        try {
            const response = await fetch('http://localhost:8080/api/v1/chat', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ prompt: prompt })
            });

            if (!response.ok) {
                throw new Error('Ошибка сети');
            }

            const reader = response.body.getReader();
            const decoder = new TextDecoder();

            while (true) {
                const { done, value } = await reader.read();
                if (done) break;

                const chunk = decoder.decode(value, { stream: true });

                responseContainer.textContent += JSON.parse(chunk.replace('data:', '')).chunk


                responseContainer.scrollTop = responseContainer.scrollHeight;
            }
        } catch (error) {
            console.error('Ошибка:', error);
            responseContainer.textContent = 'Произошла ошибка: ' + error.message;
        } finally {
            submitBtn.disabled = false;
        }
    }

    // Обработчик для кнопки отправки
    submitBtn.addEventListener('click', sendPrompt);

    // Обработчик для кнопки очистки
    clearBtn.addEventListener('click', function() {
        responseContainer.textContent = '';
    });

    // Обработчик для клавиши Enter в поле ввода
    promptInput.addEventListener('keydown', function(event) {
        if (event.key === 'Enter' && !event.shiftKey) {
            event.preventDefault();
            sendPrompt();
        }
    });
});