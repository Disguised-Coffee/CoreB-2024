# Core B 2024 Code

We're getting there!

### SEARCHING FOR TAGS

### The concept

<b>make sure to configure the apriltag's position!</b>
<ol>
    <li>Spin to find an April Tag</li>
    <li>Once in sight, begin going towards April Tag using PIDController</li>
    <li>When moving towards April tag, center with Tag
        <ul>
            <li> If the bot is to far right, more towards the left</li>
            <li> If the bot is to far left, more towards the right</li>
        </ul>
    </li>
</ol>
We want to keep moving towards the tag until we stop for 12ish seconds.<br>

Note, it would be good to implement a 

### Known problems

<ol>
    <li> 
        What about multiple April tags? How do we make it so that the Robot only<br/>
        Well JSON dumps might help us:<br/>
        <a href="https://docs.limelightvision.io/docs/docs-limelight/apis/json-dump-specification"> https://docs.limelightvision.io/docs/docs-limelight/apis/json-dump-specification</a><br/>
        <a href="https://github.com/LimelightVision/limelightlib-wpijava">
        https://github.com/LimelightVision/limelightlib-wpijava</a><br/><br/><br/>
        <b><i>Look into 'Json Dumps'</i></b>
    </li>
</ol>
