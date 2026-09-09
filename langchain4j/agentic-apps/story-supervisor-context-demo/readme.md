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
java -jar ./target/supervisor-customization-demo-1.0.0.jar
```

If everything is configured correctly, the application will start, and you will be able to see following messages in the console.

```code
$ java -jar ./target/story-supervisor-context-demo-1.0.0.jar 

==========================================
     STORY SUPERVISOR DEMO
==========================================

Enter your story idea: 
```
When prompted Give a story idea like "Story About a Sage in India" and some quality threshold value like 8.

```code
$ java -jar ./target/story-supervisor-context-demo-1.0.0.jar 

==========================================
     STORY SUPERVISOR DEMO
==========================================

Enter your story idea: Story About a Sage in India
Enter quality threshold (1-10): 8

==========================================
SUPERVISOR CONTEXT
==========================================
You are supervising a storytelling team.

Available specialist agents:

1. storyCreator
   Creates the initial story.

2. storyReviewer
   Reviews the current story and produces:
   - quality score
   - thresholdReached
   - improvement feedback

3. storyEditor
   Improves an existing story using the
   latest reviewer feedback.

STORY POLICIES:

- Stories must be family-friendly.
- Target audience is children aged 8-10.
- Use simple and engaging English.
- Prefer a fun and adventurous tone.
- Avoid graphic violence.
- Keep the story reasonably concise.
- The story should have a clear beginning,
  middle, and ending.

QUALITY TARGET:

The required quality score is 8 out of 10.

SUPERVISION RULES:

- If no story exists yet, invoke storyCreator.

- Once a story exists, invoke storyReviewer
  to evaluate it.

- Carefully inspect the StoryReview returned
  by storyReviewer.

- If thresholdReached is false, the story
  is NOT finished.

- When thresholdReached is false, invoke
  storyEditor using the latest review feedback.

- After storyEditor improves the story,
  invoke storyReviewer again.

- Continue the review/improvement cycle while
  meaningful improvements are required.

- Do not invoke storyCreator again merely to
  improve an existing story. Use storyEditor.

- Do not finish the task when the latest
  StoryReview has thresholdReached=false.

- Finish when the latest StoryReview has
  thresholdReached=true.

- Once the requested quality threshold has
  been reached, do not perform unnecessary
  additional editing.

The goal is to produce the best final story
while reaching the requested quality threshold.


--------------------------------------------------
Invoking Agent : storySupervisor
--------------------------------------------------

--------------------------------------------------
Invoking Agent : storyCreator
--------------------------------------------------

Agent Completed : storyCreator

--------------------------------------------------
Invoking Agent : storyReviewer
--------------------------------------------------

Agent Completed : storyReviewer
Quality Score   : 8
Target Reached  : true

Reviewer Feedback:
The story meets the expected quality as it has a clear beginning, middle, and ending, and the pacing is well-balanced. The character consistency is good, and the story is engaging and fun. The ending is satisfying, and the message about kindness and compassion is well conveyed. However, the story could benefit from more descriptive language and sensory details to make the setting and characters more vivid. Additionally, some of the phrases, such as 'the greatest healers are not those who possess ancient secrets, but those who possess a heart full of love and a spirit full of wonder,' feel a bit clichéd and could be rephrased for more impact.

--------------------------------------------------
Invoking Agent : storyReviewer
--------------------------------------------------

Agent Completed : storyReviewer
Quality Score   : 8
Target Reached  : true

Reviewer Feedback:
The story meets the expected quality as it has a clear beginning, middle, and ending, and the pacing is well-balanced. The character consistency is good, and the story is engaging and fun. The ending is satisfying, and the message about kindness and compassion is well conveyed. However, the story could benefit from more descriptive language and sensory details to make the setting and characters more vivid. Additionally, some of the phrases, such as 'the greatest healers are not those who possess ancient secrets, but those who possess a heart full of love and a spirit full of wonder,' feel a bit clichéd and could be rephrased for more impact.

--------------------------------------------------
Invoking Agent : storyReviewer
--------------------------------------------------

Agent Completed : storyReviewer
Quality Score   : 8
Target Reached  : true

Reviewer Feedback:
The story meets the expected quality as it has a clear beginning, middle, and ending, and the pacing is well-balanced. The character consistency is good, and the story is engaging and fun. The ending is satisfying, and the message about kindness and compassion is well conveyed. However, the story could benefit from more descriptive language and sensory details to make the setting and characters more vivid. Additionally, some of the phrases, such as 'the greatest healers are not those who possess ancient secrets, but those who possess a heart full of love and a spirit full of wonder,' feel a bit clichéd and could be rephrased for more impact.

--------------------------------------------------
Invoking Agent : storyReviewer
--------------------------------------------------

Agent Completed : storyReviewer
Quality Score   : 8
Target Reached  : true

Reviewer Feedback:
The story meets the expected quality as it has a clear beginning, middle, and ending, and the pacing is well-balanced. The character consistency is good, and the story is engaging and fun. The ending is satisfying, and the message about kindness and compassion is well conveyed. However, the story could benefit from more descriptive language and sensory details to make the setting and characters more vivid. Additionally, some of the phrases, such as 'the greatest healers are not those who possess ancient secrets, but those who possess a heart full of love and a spirit full of wonder,' feel a bit clichéd and could be rephrased for more impact.

--------------------------------------------------
Invoking Agent : storyReviewer
--------------------------------------------------

Agent Completed : storyReviewer
Quality Score   : 8
Target Reached  : true

Reviewer Feedback:
The story meets the expected quality as it has a clear beginning, middle, and ending, and the pacing is well-balanced. The character consistency is good, and the story is engaging and fun. The ending is satisfying, and the message about kindness and compassion is well conveyed. However, the story could benefit from more descriptive language and sensory details to make the setting and characters more vivid. Additionally, some of the phrases, such as 'the greatest healers are not those who possess ancient secrets, but those who possess a heart full of love and a spirit full of wonder,' feel a bit clichéd and could be rephrased for more impact.

--------------------------------------------------
Invoking Agent : storyReviewer
--------------------------------------------------

Agent Completed : storyReviewer
Quality Score   : 8
Target Reached  : true

Reviewer Feedback:
The story meets the expected quality as it has a clear beginning, middle, and ending, and the pacing is well-balanced. The character consistency is good, and the story is engaging and fun. The ending is satisfying, and the message about kindness and compassion is well conveyed. However, the story could benefit from more descriptive language and sensory details to make the setting and characters more vivid. Additionally, some of the phrases, such as 'the greatest healers are not those who possess ancient secrets, but those who possess a heart full of love and a spirit full of wonder,' feel a bit clichéd and could be rephrased for more impact.

--------------------------------------------------
Invoking Agent : storyReviewer
--------------------------------------------------

Agent Completed : storyReviewer
Quality Score   : 8
Target Reached  : true

Reviewer Feedback:
The story meets the expected quality as it has a clear beginning, middle, and ending, and the pacing is well-balanced. The character consistency is good, and the story is engaging and fun. The ending is satisfying, and the message about kindness and compassion is well conveyed. However, the story could benefit from more descriptive language and sensory details to make the setting and characters more vivid. Additionally, some of the phrases, such as 'the greatest healers are not those who possess ancient secrets, but those who possess a heart full of love and a spirit full of wonder,' feel a bit clichéd and could be rephrased for more impact.

--------------------------------------------------
Invoking Agent : storyReviewer
--------------------------------------------------

Agent Completed : storyReviewer
Quality Score   : 8
Target Reached  : true

Reviewer Feedback:
The story meets the expected quality as it has a clear beginning, middle, and ending, and the pacing is well-balanced. The character consistency is good, and the story is engaging and fun. The ending is satisfying, and the message about kindness and compassion is well conveyed. However, the story could benefit from more descriptive language and sensory details to make the setting and characters more vivid. Additionally, some of the phrases, such as 'the greatest healers are not those who possess ancient secrets, but those who possess a heart full of love and a spirit full of wonder,' feel a bit clichéd and could be rephrased for more impact.

--------------------------------------------------
Invoking Agent : storyReviewer
--------------------------------------------------

Agent Completed : storyReviewer
Quality Score   : 8
Target Reached  : true

Reviewer Feedback:
The story meets the expected quality as it has a clear beginning, middle, and ending, and the pacing is well-balanced. The character consistency is good, and the story is engaging and fun. The ending is satisfying, and the message about kindness and compassion is well conveyed. However, the story could benefit from more descriptive language and sensory details to make the setting and characters more vivid. Additionally, some of the phrases, such as 'the greatest healers are not those who possess ancient secrets, but those who possess a heart full of love and a spirit full of wonder,' feel a bit clichéd and could be rephrased for more impact.

--------------------------------------------------
Invoking Agent : storyReviewer
--------------------------------------------------

Agent Completed : storyReviewer
Quality Score   : 8
Target Reached  : true

Reviewer Feedback:
The story meets the expected quality as it has a clear beginning, middle, and ending, and the pacing is well-balanced. The character consistency is good, and the story is engaging and fun. The ending is satisfying, and the message about kindness and compassion is well conveyed. However, the story could benefit from more descriptive language and sensory details to make the setting and characters more vivid. Additionally, some of the phrases, such as 'the greatest healers are not those who possess ancient secrets, but those who possess a heart full of love and a spirit full of wonder,' feel a bit clichéd and could be rephrased for more impact.

--------------------------------------------------
Invoking Agent : storyReviewer
--------------------------------------------------

Agent Completed : storyReviewer
Quality Score   : 8
Target Reached  : true

Reviewer Feedback:
The story meets the expected quality as it has a clear beginning, middle, and ending, and the pacing is well-balanced. The character consistency is good, and the story is engaging and fun. The ending is satisfying, and the message about kindness and compassion is well conveyed. However, the story could benefit from more descriptive language and sensory details to make the setting and characters more vivid. Additionally, some of the phrases, such as 'the greatest healers are not those who possess ancient secrets, but those who possess a heart full of love and a spirit full of wonder,' feel a bit clichéd and could be rephrased for more impact.

Agent Completed : storySupervisor

==========================================
FINAL STORY
==========================================

The Sage of the Golden Temple

In the bustling city of Amritsar, India, there was a small, mysterious shop tucked away in a quiet alley. The sign above the door read "Sage's Secrets," and the windows were filled with an assortment of colorful spices, rare herbs, and peculiar trinkets. The shop was run by a wise and kind sage named Ravi, who was known throughout the city for his extraordinary knowledge and healing powers.

One sunny afternoon, a curious young boy named Rohan wandered into the shop, searching for a cure for his ailing mother. Rohan's mother had been suffering from a mysterious illness that had left her weak and tired. Desperate for a solution, Rohan had heard whispers of Ravi's magical abilities and had come to seek his help.

Ravi welcomed Rohan with a warm smile and invited him to sit by the fire. As they sipped sweet, spiced tea, Ravi listened intently to Rohan's story. He nodded thoughtfully, his eyes twinkling with understanding, and began to rummage through his shelves of ancient tomes and mysterious artifacts.

After a few moments of searching, Ravi produced a small, delicate vial filled with a shimmering liquid. "This is the essence of the Golden Lotus," he explained, "a rare and precious flower that blooms only once a year, under the light of the full moon. Its petals hold the power to heal even the most stubborn of ailments."

Rohan's eyes widened with wonder as Ravi handed him the vial. "But be warned, young one," Ravi cautioned, "the Golden Lotus's power comes with a price. You must use its essence with kindness and compassion, and only for the greater good."

With the vial safely in hand, Rohan rushed back to his mother's bedside. He carefully poured the shimmering liquid into a small bowl, and as the essence touched her skin, a warm, golden light began to emanate from her body. Slowly but surely, Rohan's mother began to stir, her strength and vitality returning with each passing moment.

Overjoyed, Rohan rushed back to Ravi's shop, vial in hand, to thank the sage for his incredible gift. Ravi smiled, his eyes shining with warmth, and said, "The true magic, young one, was not in the Golden Lotus itself, but in the kindness and compassion with which you used its power. Remember, the greatest healers are not those who possess ancient secrets, but those who possess a heart full of love and a spirit full of wonder."
```