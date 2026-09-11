## Build the Project

Open a terminal and navigate to the directory containing the `pom.xml` file. Then build the project using the following command:

```bash
mvn clean package
```

---

## Run the Application

Once the build completes successfully, the executable JAR file will be generated in the `target` directory.

Start the application using the following command:

```bash
java -jar ./target/custom-planner-sequential-story-workflow-1.0.0.jar 
```

If everything is configured correctly, the application will start, and you will be able to see following messages in the console.

```code
$ java -jar ./target/custom-planner-sequential-story-workflow-1.0.0.jar 

======================================
Initializing CustomSequentialPlanner
======================================
0 -> storyWriter
1 -> storyReviewer
2 -> storyEditor

Planner decision: invoke agent [0] -> storyWriter
Planner decision: invoke agent [1] -> storyReviewer
Planner decision: invoke agent [2] -> storyEditor

All agents executed. Workflow completed.

======================================
FINAL STORY
======================================

The sun beat down on the dusty trail, relentless in its ferocity. Dr. Maria Rodriguez, a renowned geologist with a passion for uncovering the secrets of the past, trudged through the scrubby underbrush, her eyes fixed on the ancient map etched into the parchment. She had spent years searching for this place, pouring over dusty tomes and crumbling scrolls, and finally, she had found it.

As she rounded a bend in the trail, the mountain loomed before her, its peak shrouded in a veil of mist. Maria's heart quickened with excitement. This was it. This was the place where the ancient civilization of the Aetherians was said to have hidden their most treasured secrets.

She began to climb, her hands and feet finding holds in the rocky face. The air grew cooler, the silence more profound, as she ascended higher. Finally, she reached the entrance to the hidden city, a massive stone door adorned with intricate carvings that seemed to dance in the fading light.

Maria pushed the door open, and a warm golden light spilled out, bathing her in its radiance. She stepped inside, her eyes adjusting to the dim light. The city was breathtaking, with towering spires and grand architecture that seemed to defy gravity. The air was thick with the scent of aged stone and the whispers of the past.

As she explored the city, Maria encountered a young man named Kanaq, who claimed to be the last of his people. He was wary at first, but Maria's kind eyes and gentle manner soon put him at ease. Together, they navigated the city's winding streets, marveling at the ancient artifacts and mysterious devices that seemed to hold secrets of their own.

As the days passed, Maria and Kanaq grew closer, their bond forged in the shared wonder of discovery. But as they delved deeper into the city, they began to uncover secrets that threatened to upend everything they thought they knew. They discovered ancient texts that spoke of a powerful technology hidden beneath the earth, and devices that seemed to manipulate the very fabric of reality.

Maria's curiosity was piqued, and she became increasingly obsessed with uncovering the truth. Kanaq, on the other hand, seemed to be hiding something, his eyes clouding over with a deep sadness. Maria couldn't help but wonder what secrets he was keeping, and what lay behind his enigmatic smile.

One night, as they sat on a hill overlooking the city, Kanaq turned to Maria with a look of grave concern. "The city is not what it seems," he said, his voice barely above a whisper. "We must leave, now."

Maria's heart skipped a beat. What did Kanaq mean? But before she could ask, a low rumble shook the ground, and the sky grew dark. The city was awakening, and Maria knew that she had to get out – fast.

With Kanaq by her side, Maria fled the city, the ancient door slamming shut behind them. As they emerged into the bright sunlight, Maria turned to Kanaq with a question. "What secrets did we uncover?"

Kanaq's eyes locked onto hers, and for a moment, Maria saw a glimmer of something ancient, something powerful. "The city is not just a city," he said, his voice low and mysterious. "It's a doorway. And we've just passed through it."

As Maria gazed back at the mountain, she saw that the mist had cleared, revealing a path that wound its way into the heart of the peak. And she knew that she would return, drawn by the secrets that lay hidden beneath the earth. But this time, she would be prepared, and she would uncover the truth that lay hidden beneath the surface.

The sun dipped below the horizon, casting the mountain in a warm orange glow. Maria and Kanaq stood at the edge of the city, their eyes fixed on the path that lay before them. They knew that they would never be the same again, that their lives had been forever changed by the secrets they had uncovered. And as they walked away from the city, they knew that they would always be bound together by the mysteries of the Aetherians.
```
