// focus.js
document.addEventListener('DOMContentLoaded', () => {
    const track = document.getElementById('card-track');
    const cards = Array.from(document.getElementsByClassName('focus-card'));
    const prevBtn = document.getElementById('prevBtn');
    const nextBtn = document.getElementById('nextBtn');
    const exitBtn = document.getElementById('exitBtn'); // can be <a> or <button>
    const prog = document.getElementById('progress');   // <progress value max>
    const progLabel = document.getElementById('progressLabel'); // optional <output>

    let activeIndex = 0;

    // If you support deep links like #card-2:
    const hashMatch = location.hash.match(/card-(\d)/);
    if (hashMatch) activeIndex = Math.min(4, Math.max(0, parseInt(hashMatch[1], 10)));

    function show(i) {
        // clamp
        i = Math.max(0, Math.min(cards.length - 1, i));
        // hide old / show new
        cards.forEach((el, idx) => {
            const isActive = idx === i;
            el.setAttribute('aria-hidden', String(!isActive));
            el.classList.toggle('is-active', isActive);
        });
        // move focus to the active card's heading (assumes <h2 tabindex="-1"> inside)
        const heading = cards[i].querySelector('h2');
        if (heading) heading.focus({ preventScroll: false });

        // progress UI
        if (prog) {
            prog.max = cards.length;
            prog.value = i + 1;
        }
        if (progLabel) progLabel.textContent = `${i + 1}/${cards.length}`;

        // buttons state
        prevBtn?.toggleAttribute('disabled', i === 0);
        nextBtn?.toggleAttribute('disabled', i === cards.length - 1);

        // url hash (optional)
        history.replaceState(null, '', `#card-${i}`);

        activeIndex = i;
    }

    function showPrev() { if (activeIndex > 0) show(activeIndex - 1); }
    function showNext() { if (activeIndex < cards.length - 1) show(activeIndex + 1); }

    // wire buttons
    prevBtn?.addEventListener('click', showPrev);
    nextBtn?.addEventListener('click', showNext);
    // if exitBtn is a <button>, navigate; if it's an <a>, you can skip this
    if (exitBtn && exitBtn.tagName === 'BUTTON') {
        exitBtn.addEventListener('click', () => history.back());
    }

    // keyboard
    document.addEventListener('keydown', (e) => {
        if (e.key === 'ArrowLeft') { e.preventDefault(); showPrev(); }
        else if (e.key === 'ArrowRight') { e.preventDefault(); showNext(); }
        else if (e.key === 'Escape') { exitBtn ? exitBtn.click() : history.back(); }
    });

    // init
    show(activeIndex);
});
