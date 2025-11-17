import 'bootstrap';
// Swiper 9+ modulárisan:
import Swiper from 'swiper';
import { Navigation, Pagination, Thumbs, EffectFade } from 'swiper/modules';
// Chocolat lightbox
import Chocolat from 'chocolat';
// Jarallax
import { jarallax } from 'jarallax';
// Chart.js: https://www.chartjs.org/docs/latest/getting-started/integration.html
import { Chart, registerables } from 'chart.js';

// Chart.js komponensek regisztrálása
Chart.register(...registerables);

// Utils
const $$ = (sel, ctx = document) =>
  Array.from(ctx.querySelectorAll(sel));

// Chocolat
function initChocolat() {
  const links = document.querySelectorAll('.image-link');
  if (links.length) {
    Chocolat(links, {
      imageSize: 'contain',
      loop: true,
    });
  }
}

// Swiper
function initSwiper() {
  // Fő slider
  const mainSwiperEl = document.querySelector('.main-swiper');
  if (mainSwiperEl) {
    new Swiper(mainSwiperEl, {
      modules: [Pagination],
      speed: 500,
      pagination: { el: '.swiper-pagination', clickable: true },
    });
  }

  // Kategória carousel
  const categoryCarouselEl = document.querySelector('.category-carousel');
  if (categoryCarouselEl) {
    new Swiper(categoryCarouselEl, {
      modules: [Navigation],
      slidesPerView: 8,
      spaceBetween: 30,
      speed: 500,
      navigation: {
        nextEl: '.category-carousel-next',
        prevEl: '.category-carousel-prev',
      },
      breakpoints: {
        0: { slidesPerView: 2 },
        768: { slidesPerView: 3 },
        991: { slidesPerView: 5 },
        1500: { slidesPerView: 8 },
      },
    });
  }

  // Több products-carousel példány
  $$('.products-carousel').forEach((wrap) => {
    // a wrap-en belül keressük az elemeket, nem ID-re támaszkodunk
    const container = wrap.querySelector('.swiper');
    const nextBtn = wrap.querySelector('.products-carousel-next');
    const prevBtn = wrap.querySelector('.products-carousel-prev');

    if (container) {
      new Swiper(container, {
        modules: [Navigation],
        slidesPerView: 5,
        spaceBetween: 30,
        speed: 500,
        navigation: nextBtn && prevBtn ? { nextEl: nextBtn, prevEl: prevBtn } : {},
        breakpoints: {
          0: { slidesPerView: 1 },
          768: { slidesPerView: 3 },
          991: { slidesPerView: 4 },
          1500: { slidesPerView: 5 },
        },
      });
    }
  });

  // Termék oldal: thumb + large
  const thumbsEl = document.querySelector('.product-thumbnail-slider');
  const largeEl = document.querySelector('.product-large-slider');

  if (thumbsEl && largeEl) {
    const thumbSlider = new Swiper(thumbsEl, {
      slidesPerView: 5,
      spaceBetween: 20,
      direction: window.innerWidth >= 992 ? 'vertical' : 'horizontal',
      on: {
        resize(swiper) {
          swiper.changeDirection(window.innerWidth >= 992 ? 'vertical' : 'horizontal');
        },
      },
    });

    new Swiper(largeEl, {
      modules: [Thumbs, EffectFade, Pagination],
      slidesPerView: 1,
      spaceBetween: 0,
      effect: 'fade',
      thumbs: { swiper: thumbSlider },
      pagination: { el: '.swiper-pagination', clickable: true },
    });
  }
}

// Jarallax
function initJarallax() {
  const j1 = document.querySelectorAll('.jarallax');
  const j2 = document.querySelectorAll('.jarallax-keep-img');

  if (j1.length) jarallax(j1);
  if (j2.length) jarallax(j2, { keepImg: true });
}

// Chart.js

/**
 * A diagram adatait leíró objektum.
 * @typedef {object} DiagramPayload
 * @property {string[]} labels - A tengelyen megjelenő címkék.
 * @property {number[]} revenues - A bevételek értékei.
 */

/**
 * Kiolvassa és feldolgozza a diagram adatait a DOM-ból.
 * @returns {DiagramPayload | null} Az adatok objektuma, vagy null, ha nem található.
 */
function readDiagramPayload() {
  const el = document.getElementById('diagram-payload');
  if (!el) return null;
  try {
    return JSON.parse(el.textContent || '{}');
  } catch {
    console.warn('Invalid diagram payload JSON');
    return null;
  }
}

const fmtHU = (n) => new Intl.NumberFormat('hu-HU').format(n);

function initChartJs() {
  const payload = readDiagramPayload();
  const canvas = document.getElementById('revenueChart');

  if (!payload || !canvas) return; // nincs diagram ezen az oldalon

  const data = {
    labels: payload.labels,
    datasets: [
      {
        type: 'bar',
        label: 'Bevétel (Ft)',
        data: payload.revenues,
        backgroundColor: '#69b3a2',
        borderColor: '#5aa792',
        borderWidth: 0,
        hoverBackgroundColor: '#4c9783',
        hoverBorderColor: '#3f7a6b',
      },
    ],
  };

  const options = {
    responsive: true,
    maintainAspectRatio: false,
    interaction: { mode: 'nearest', intersect: true },
    plugins: {
      legend: { display: false },
      title: { display: true, text: 'Legtöbb termék – Bevétel' },
      tooltip: {
        callbacks: {
          label: (ctx) => ` Bevétel: ${fmtHU(Number(ctx.parsed.y))} Ft`,
        },
      },
    },
    scales: {
      x: { ticks: { maxRotation: 45, minRotation: 0 } },
      y: {
        position: 'left',
        title: { display: true, text: 'Bevétel (Ft)' },
        ticks: { callback: v => fmtHU(Number(v)) },
        grid: { drawOnChartArea: true },
      },
    },
  };

  new Chart(canvas, { type: 'bar', data, options });
}

// Initializálás
document.addEventListener('DOMContentLoaded', () => {
  initSwiper();
  initJarallax();
  initChocolat();
  initChartJs();
});
