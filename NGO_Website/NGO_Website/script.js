document.addEventListener("DOMContentLoaded", () => {

  // ===== Highlight active navigation button =====
  const navButtons = document.querySelectorAll("nav button");
  navButtons.forEach(btn => {
    btn.addEventListener("click", function () {
      navButtons.forEach(b => b.classList.remove("active-btn"));
      this.classList.add("active-btn");
    });
  });

  // ===== Simple form validation =====
  const form = document.querySelector("form");
  if (form) {
    form.addEventListener("submit", function (e) {
      const email = form.querySelector("input[type='email']");
      if (email && !email.value.includes("@")) {
        e.preventDefault();
        alert("Please enter a valid email address.");
        return;
      }

      // ===== EmailJS Form Submission =====
      e.preventDefault();

      const serviceID = 'DailyBreadService';
      const templateID = 'template_in0hsyd';
      const publicKey = 'A-zY5gtOYydjHJEaw';

      emailjs.sendForm(serviceID, templateID, form, publicKey)
        .then(() => {
          alert("Message sent successfully!");
          form.reset();
        })
        .catch((err) => {
          console.error("EmailJS error:", err);
          alert("Oops! Something went wrong. Please try again later.");
        });
    });
  }

  // ===== Footer placeholder =====
  const footerPlaceholder = document.getElementById('footer-placeholder');
  if (footerPlaceholder) {
    fetch('footer.html')
      .then(response => response.text())
      .then(data => footerPlaceholder.innerHTML = data)
      .catch(err => console.error("Failed to load footer:", err));
  }

  // ===== Highlight current page button =====
  const currentPage = window.location.pathname.split("/").pop().toLowerCase();
  const buttonMap = {
    "index.html": 0,
    "gallery.html": 1,
    "services.html": 2,
    "sponsors.html": 3,
    "about.html": 4,
    "contact.html": 5
  };
  const index = buttonMap[currentPage] ?? 0;
  if (navButtons[index]) navButtons[index].classList.add("active-btn");

  // ===== HERO BACKGROUND SWITCH =====
  const hero = document.querySelector('.hero');
  if (hero) {
    const heroImages = ['image/children.jpg', 'image/children2.jpg'];
    let heroIndex = 0;

    setInterval(() => {
      heroIndex = (heroIndex + 1) % heroImages.length;
      hero.style.background = `
        linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)),
        url('${heroImages[heroIndex]}') no-repeat center center / cover
      `;
    }, 7000);
  }

  // ===== LIGHTBOX =====
  const galleryImages = document.querySelectorAll(".gallery img");
  const lightbox = document.getElementById("lightbox");
  const lightboxImg = document.getElementById("lightbox-img");
  const closeBtn = document.querySelector(".close");

  if (galleryImages.length && lightbox && lightboxImg && closeBtn) {
    galleryImages.forEach(img => {
      img.addEventListener("click", () => {
        lightbox.style.display = "block";
        lightboxImg.src = img.src;
      });
    });

    closeBtn.addEventListener("click", () => lightbox.style.display = "none");
    lightbox.addEventListener("click", (e) => {
      if (e.target === lightbox) lightbox.style.display = "none";
    });
  }

  // ===== GALLERY ROUND-ROBIN WITH CAPTIONS =====
  const gallery = [
    {
      id: "food1",
      images: ["image/food1.jpg", "image/food1b.jpg", "image/food1c.jpg"],
      captions: ["Feeding Hope, One Meal at a Time.", "Serving fresh meals", "Sharing love through food"]
    },
    {
      id: "food2",
      images: ["image/food2.jpg", "image/food2b.jpg", "image/food2c.jpg"],
      captions: ["Together, We Make a Difference.", "Helping hands in action", "Delivering daily bread"]
    },
    {
      id: "food3",
      images: ["image/food3.jpg", "image/food3b.jpg", "image/food3c.jpg"],
      captions: ["Building Stronger Communities.", "Hope in every meal", "Smiles that feed souls"]
    },
    {
      id: "food4",
      images: ["image/food4.jpg", "image/food4b.jpg", "image/food4c.jpg"],
      captions: ["Spreading Love, Sharing Bread.", "Kindness in every bite", "Love that nourishes all"]
    }
  ];

  // Track current image index for each item
  gallery.forEach(item => item.current = 0);
  let roundIndex = 0;

  function advanceOneImage() {
    const item = gallery[roundIndex];
    const imgEl = document.getElementById(item.id);
    const captionEl = document.getElementById(`caption${roundIndex+1}`);

    if (!imgEl || !captionEl) {
      roundIndex = (roundIndex + 1) % gallery.length;
      return;
    }

    imgEl.classList.add('fade-out');
    captionEl.classList.add('fade-out');

    setTimeout(() => {
      item.current = (item.current + 1) % item.images.length;
      imgEl.src = item.images[item.current];
      captionEl.textContent = item.captions[item.current];

      imgEl.classList.remove('fade-out');
      captionEl.classList.remove('fade-out');
    }, 800);

    roundIndex = (roundIndex + 1) % gallery.length;
  }

  setInterval(advanceOneImage, 3000);

  // ===== HERO TYPING EFFECT =====
  const titleEl = document.getElementById("hero-title");
  const subtitleEl = document.getElementById("hero-subtitle");
  const titleText = "Daily Bread Foundation";
  const subtitleText = "Feeding Hope. One Meal at a Time.";
  const typingSpeed = 120;
  const pauseTime = 7000;

  function typeText(element, text) {
    return new Promise(resolve => {
      if (!element) return resolve();
      let i = 0;
      element.textContent = "";
      const interval = setInterval(() => {
        element.textContent += text[i];
        i++;
        if (i === text.length) {
          clearInterval(interval);
          resolve();
        }
      }, typingSpeed);
    });
  }

  if (titleEl && subtitleEl) {
    async function typingCycle() {
      await typeText(titleEl, titleText);
      await typeText(subtitleEl, subtitleText);
      await new Promise(r => setTimeout(r, pauseTime));
      titleEl.textContent = "";
      subtitleEl.textContent = "";
    }

    typingCycle();
    const cycleTime = pauseTime + (titleText.length + subtitleText.length) * typingSpeed + 200;
    setInterval(typingCycle, cycleTime);
  }

});
