let root = $('#root');
let tweetContainer = $(`<div class = 'container'></div>`);
$(document).ready(() => {
    let container = $('<div class = "container"></div>');
    $(window).on('scroll', () => {
        if ($(window).scrollTop() == 0) {
            numTweets = 50;
            loadNewTweets();
        }
        if ($(window).scrollTop() + $(window).height() == $(document).height()) {
            loadMoreTweets();
        }
    })
    root.append(container);
    let newTweetButton = $(`<button class= "button is-rounded is-info mb-4 mt-4" id="newTweetBtn">New Tweet</button>`);
    let tweetButton = $(`<button class= "button is-rounded is-info mb-4 mt-4" id="tweetBtn" type = "submit">Tweet</button>`);
    let control = $('<div class = "control"></div>');
    container.append(control);

    let newTweetButtonClicked = () => {
        control.prepend(tweetButton);
        tweetButton.on('click', tweetButtonClicked);
        control.prepend(`<textarea id = "newTweetBody" maxlength = "280" class = "textarea mt-4 is-info" placeholder = "What's on your mind?"></textarea>`);
        newTweetButton.remove();
    };

    let tweetButtonClicked = async () => {
        let tweetBody = $('#newTweetBody').val();
        await axios({
            method: 'post',
            url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets',
            withCredentials: true,
            data: {
                body: tweetBody
            },
        });
        loadNewTweets();
        control.prepend(newTweetButton);
        newTweetButton.on('click', newTweetButtonClicked);
        tweetButton.remove();
        $('#newTweetBody').remove();
    };
    newTweetButton.on('click', newTweetButtonClicked);
    control.append(newTweetButton);
    root.append(tweetContainer);
    loadTweets();
});

let numTweets = 50;
const loadTweets = async () => {
    const tweets = await getAllTweets();
    tweets.forEach(tweet => {
        let box = createTweet(tweet);
        tweetContainer.append(box);
    });
}

const loadNewTweets = async () => {
    const allTweets = await getAllTweets();
    tweetContainer.empty();
    allTweets.forEach(tweet => {
        let box = createTweet(tweet);
        tweetContainer.append(box);
    });
}

const loadMoreTweets = async () => {
    const newTweets = await axios({
        method: 'get',
        url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets',
        withCredentials: true,
        params: {
            skip: numTweets,
            limit: 3
        }
    });
    newTweets.data.forEach((tweet) => {
        tweetContainer.append(createTweet(tweet));
    });
    numTweets += 3;
}

const createTweet = (tweet) => {
    let box = $(`<div class = "box"></div>`);
    let container = $('<div class = "container"></div>');
    let content = $(`<div class = "content"></div>`);
    let body = tweet.body.slice(0, 280);
    let header = $(`<div class = "columns is-multiline"></div>`);
    header.append(`<div class = "column is-8"><p><strong>${tweet.author}</strong><br>${body}</p></div>`);
    let columns = $(`<div class = "columns is-multiline"></div>`);
    columns.append(`<p class = "column" id = "${tweet.id}likeCount">${tweet.likeCount} <small>Likes</small></p>`);
    let replyText = $(`<a class = "column">${tweet.replyCount} <small>Replies</small></a>`);
    let repliesShown = false;
    let replies = $(`<div class = "mb-4"></div>`);
    replyText.on('click', async () => {
        if(repliesShown){
            replies.empty();
            repliesShown = false;
        }
        else if (tweet.replyCount > 0) {
            repliesShown = true;
            let readTweet = await getTweet(tweet.id);
            let tweetBoxes = readTweet.replies.map((reply) => createTweet(reply));
            tweetBoxes.forEach((tweetBox) => {
                tweetBox.addClass("ml-6");
                tweetBox.find('.error').remove();
                tweetBox.find('.deleteBtn').removeClass('is-offset-2').addClass('narrow');
                replies.append(tweetBox);
            });
            replies.insertAfter(box);
        }
    });
    columns.append(replyText);
    columns.append(`<p class = "column">${tweet.retweetCount} <small>Retweets</small></p>`);
    let buttons = $(`<div class = "columns is-multiline"></div>`);
    let likeColumn = $(`<div class = "column"></div>`);
    let unlikeButton = $(`<button class = "button is-info is-outlined" id = "unlike">Liked</button>`);
    let likeButton = $(`<button class = "button is-info is-outlined" id = "like">Like</button>`);
    let unlikeFunction = async () => {
        await axios({
            method: 'put',
            url: `https://comp426-1fa20.cs.unc.edu/a09/tweets/${tweet.id}/unlike`,
            withCredentials: true,
        });
        likeColumn.append(likeButton);
        likeButton.on('click', likeFunction);
        unlikeButton.remove();

        let newTweet = await getTweet(tweet.id);
        $(`#${tweet.id}likeCount`).html(`${newTweet.likeCount} <small>Likes</small>`);
    }
    let likeFunction = async () => {
        await axios({
            method: 'put',
            url: `https://comp426-1fa20.cs.unc.edu/a09/tweets/${tweet.id}/like`,
            withCredentials: true,
        });
        likeColumn.append(unlikeButton);
        unlikeButton.on('click', unlikeFunction);
        likeButton.remove();

        let newTweet = await getTweet(tweet.id);
        $(`#${tweet.id}likeCount`).html(`${newTweet.likeCount} <small>Likes</small>`);
    }
    if (tweet.isLiked) {
        likeColumn.append(unlikeButton);
        unlikeButton.on('click', unlikeFunction);
    }
    else {
        likeColumn.append(likeButton);
        likeButton.on('click', likeFunction);
    }
    buttons.append(likeColumn);
    let replyColumn = $(`<div class = "column"></div>`);
    let replyButton = $(`<button class = "button is-info is-outlined">Reply</button>`);
    replyButton.on('click', () => {
        content.empty();
        content.append(`<div class = "content"><p><strong>${tweet.author}</strong><br>${body}</p></div>`);
        content.append(`<textarea class = 'textarea is-info' maxlength = '280' id = '${tweet.id}reply' placeholder = 'Say something...'></textarea>`);
        let btnColumns = $(`<div class = "columns is-multiline"></div>`);
        let confirmColumn = $(`<div class = "column"></div>`);
        let cancelColumn = $(`<div class = "column"></div>`);
        let replyBtn = $(`<button class = 'button is-info mt-2'>Reply</button>`);
        replyBtn.on('click', async () => {
            let replyBody = $(`#${tweet.id}reply`).val();
            replyTweet(tweet.id, replyBody);
        });
        let cancelBtn = $(`<button class = "button is-danger mt-2">Cancel</button>`);
        cancelBtn.on('click', () => {
            loadNewTweets();
        });
        confirmColumn.append(replyBtn);
        cancelColumn.append(cancelBtn);
        btnColumns.append(confirmColumn);
        btnColumns.append(cancelColumn);
        content.append(btnColumns);
    });
    replyColumn.append(replyButton);
    buttons.append(replyColumn);
    let retweetColumn = $(`<div class = "column"></div>`);
    let retweetButton = $(`<button class = "button is-info is-outlined">Retweet</button>`);
    retweetButton.on('click', () => {
        content.empty();
        content.append(`<div class = "content"><p><strong>${tweet.author}</strong><br>${body}</p></div>`);
        content.append(`<textarea class = 'textarea is-info' maxlength = '280' id = '${tweet.id}retweet' placeholder = 'Add a comment...'></textarea>`);
        let btnColumns = $(`<div class = "columns is-multiline"></div>`);
        let confirmColumn = $(`<div class = "column"></div>`);
        let cancelColumn = $(`<div class = "column"></div>`);
        let retweetBtn = $(`<button class = 'button is-info mt-2'>Retweet</button>`);
        retweetBtn.on('click', async () => {
            let retweetBody = $(`#${tweet.id}retweet`).val();
            retweetTweet(tweet.id, retweetBody);
        });
        let cancelBtn = $(`<button class = "button is-danger mt-2">Cancel</button>`);
        cancelBtn.on('click', () => {
            loadNewTweets();
        });
        confirmColumn.append(retweetBtn);
        cancelColumn.append(cancelBtn);
        btnColumns.append(confirmColumn);
        btnColumns.append(cancelColumn);
        content.append(btnColumns);
    });
    retweetColumn.append(retweetButton);
    buttons.append(retweetColumn);
    if (tweet.isMine) {
        let updateButton = $(`<div class = "column"><button class = "button is-info">Update</button></div>`);
        let deleteButton = $(`<div class = "column deleteBtn is-offset-2"><button class = "button is-danger">Delete</button></div>`);
        deleteButton.on('click', async () => {
            await axios({
                method: 'delete',
                url: `https://comp426-1fa20.cs.unc.edu/a09/tweets/${tweet.id}`,
                withCredentials: true,
            });
            loadNewTweets();
        });
        updateButton.on('click', async () => {
            content.empty();
            content.append(`<textarea class = 'textarea is-info' maxlength = '280' id = '${tweet.id}update' placeholder = '${tweet.body}'>${tweet.body}</textarea>`);
            let btnColumns = $(`<div class = "columns is-multiline"></div>`);
            let updateColumn = $(`<div class = "column"></div>`);
            let cancelColumn = $(`<div class = "column"></div>`);
            let update = $(`<button class = 'button is-info mt-2'>Update</button>`);
            update.on('click', async () => {
                let updatedBody = $(`#${tweet.id}update`).val();
                await axios({
                    method: 'put',
                    url: `https://comp426-1fa20.cs.unc.edu/a09/tweets/${tweet.id}`,
                    withCredentials: true,
                    data: {
                        body: updatedBody
                    },
                });
                loadNewTweets();
            });
            let cancelBtn = $(`<button class = "button is-danger mt-2">Cancel</button>`);
            cancelBtn.on('click', () => {
                loadNewTweets();
            });
            updateColumn.append(update);
            cancelColumn.append(cancelBtn);
            btnColumns.append(updateColumn);
            btnColumns.append(cancelColumn);
            content.append(btnColumns);
        })
        header.append(updateButton);
        header.append(deleteButton);
    }
    content.append(header);
    container.append(content);
    if (tweet.type === 'retweet' || tweet.type === 'reply') {
        if (tweet.parent == null) {
            container.append('<button class= "button error" disabled>Parent tweet is unavailable.</button>');
        }
        else {
            let parentBox = $(`<div class = "box"></div>`);
            let parentContainer = $('<div class = "container"></div>');
            let parentContent = $(`<div class = "content"></div>`);
            let parentBody = $(`<p><strong>${tweet.parent.author}</strong><br>${tweet.parent.body}</p>`);
            parentContent.append(parentBody);
            parentContainer.append(parentContent);
            parentBox.append(parentContainer);
            container.append(parentBox);
        }
    }
    container.append(columns);
    container.append(buttons);
    box.append(container);
    return box;
}

const replyTweet = async (parentId, body) => {
    await axios({
        method: 'post',
        url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets',
        withCredentials: true,
        data: {
            "type": "reply",
            "parent": parentId,
            "body": body
        },
    });
    loadNewTweets();
};

const retweetTweet = async (parentId, body) => {
    await axios({
        method: 'post',
        url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets',
        withCredentials: true,
        data: {
            "type": "retweet",
            "parent": parentId,
            "body": body
        },
    });
    loadNewTweets();
}

const getTweet = async (id) => {
    let tweet = await axios({
        method: 'get',
        url: `https://comp426-1fa20.cs.unc.edu/a09/tweets/${id}`,
        withCredentials: true,
    });
    return tweet.data;
}

const getAllTweets = async () => {
    if (numTweets == 50) {
        const result = await axios({
            method: 'get',
            url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets',
            withCredentials: true,
        });
        return result.data;
    }
    else {
        let array = [];
        while (array.length <= numTweets) {
            const result = await axios({
                method: 'get',
                url: 'https://comp426-1fa20.cs.unc.edu/a09/tweets',
                withCredentials: true,
                params: {
                    skip: array.length,
                    limit: 75
                }
            });
            array = array.concat(result.data);
        }
        return array;
    }
}
